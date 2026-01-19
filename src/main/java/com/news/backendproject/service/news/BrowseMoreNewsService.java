package com.news.backendproject.service.news;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.news.backendproject.dto.news.NewsItemDTO;
import com.news.backendproject.mapper.NewsMapper;
import com.news.backendproject.mapstruct.NewsDtoMapper;
import com.news.backendproject.po.News;
import com.news.backendproject.utils.NewsFilterUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BrowseMoreNewsService {
    private final SortNewsByPublishTimeDesc sortNewsByPublishTimeDesc;
    private final GetOrSetBrowsedNewsService getOrSetBrowsedNewsService;
    @Resource
    private NewsDtoMapper newsDtoMapper;
    @Resource
    private NewsMapper newsMapper;
    //redis前缀
    private static final String BROWSERED_NEWS_KEY_PREFIX = "news:browsed:user:";
    // 已浏览记录过期时间
    private static final long BROWSERED_KEY_EXPIRE = 24L;
    // session超时标识
    private static final String SESSION_TIMEOUT_SET_FLAG = "SESSION_TIMEOUT_SET_FLAG";

    public ArrayList<NewsItemDTO> getBrowseMoreNews(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (session.getAttribute(SESSION_TIMEOUT_SET_FLAG) == null) {
            // 设置Session过期时间（24小时）
            session.setMaxInactiveInterval(86400);
            // 添加标记
            session.setAttribute(SESSION_TIMEOUT_SET_FLAG, Boolean.TRUE);
        }
        String sessionId = session.getId();
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<News>()
                .orderByDesc(News::getPublishTime);
        Page<News> page =new Page<>(1,500);
        Page<News> resultPage = newsMapper.selectPage(page, queryWrapper);
        List<News> allNews = resultPage.getRecords();
        //获取用户已浏览新闻的id
        Set<String> browsedNewsIds = getOrSetBrowsedNewsService.getBrowsedNewsIds(sessionId);

        //通过id过滤已读新闻
        List<News> unreadNews = NewsFilterUtil.filterUnreadNews(allNews, browsedNewsIds);

        //随机抽取6条新闻
        ArrayList<News> random6News = (ArrayList<News>) NewsFilterUtil.randomPickUnreadNews(unreadNews, 6, sortNewsByPublishTimeDesc);
        if (CollectionUtils.isEmpty(allNews)) {
            // 无数据时返回空集合
            return newsDtoMapper.toNewsItemDTOList(random6News);
        }
        //保存已读新闻
        getOrSetBrowsedNewsService.saveBrowsedNewsIds(sessionId, random6News);
        return newsDtoMapper.toNewsItemDTOList(random6News);
    }
}
