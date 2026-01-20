package com.news.backendproject.service.news;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.news.backendproject.dto.news.NewsItemDTO;
import com.news.backendproject.mapper.NewsMapper;
import com.news.backendproject.mapstruct.NewsDtoMapper;
import com.news.backendproject.po.News;
import com.news.backendproject.utils.NewsFilterUtil;
import com.news.backendproject.utils.SessionSetTimeoutPeriodUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrowseMoreNewsService {
    @Value("${pagenumber.max}")
    private int maxTryPages;
    private final SortNewsByPublishTimeDesc sortNewsByPublishTimeDesc;
    private final OperateBrowsedNewsService operateBrowsedNewsService;

    @Resource
    private NewsDtoMapper newsDtoMapper;
    @Resource
    private NewsMapper newsMapper;

    /**
     * 分页获取未读新闻（适配page/pageSize参数）
     * @param request 请求对象（用于获取Session）
     * @param pageNumStr 页码（前端传入，String类型转Integer）
     * @param pageSizeStr 每页条数（前端传入，String类型转Integer）
     * @return 分页后的未读新闻DTO列表 + 总条数 + 是否还有更多未读（封装为Map，适配前端分页逻辑）
     */
    public Map<String, Object> getBrowseMoreNews(HttpServletRequest request, String pageNumStr, String pageSizeStr) {
        // ==========  Session处理（保留原有逻辑） ==========
        String sessionId = SessionSetTimeoutPeriodUtil.set(request, 24);

        // ========== 分页参数校验与转换 ==========
        // 默认值：页码默认1，每页默认10条
        int pageNum = 1;
        int pageSize = 10;
        try {
            if (pageNumStr != null && !pageNumStr.trim().isEmpty()) {
                //赋值前端传入pageNumStr
                pageNum = Integer.parseInt(pageNumStr.trim());
                // 页码不能小于1
                pageNum = Math.min(pageNum, 1);
            }
            if (pageSizeStr != null && !pageSizeStr.trim().isEmpty()) {
                //赋值前端传入pageSizeStr
                pageSize = Integer.parseInt(pageSizeStr.trim());
                // 每页条数限制：1-50条（防止前端传过大值）
                pageSize = pageSize < 1 ? 10 : Math.min(pageSize, 50);
            }
        } catch (NumberFormatException e) {
            // 转换失败使用默认值
            pageNum = 1;
            pageSize = 10;
        }

        // ========== 先查询总页数 ==========
        LambdaQueryWrapper<News> countWrapper = new LambdaQueryWrapper<News>()
                .orderByDesc(News::getPublishTime);
        long totalNewsCount = newsMapper.selectCount(countWrapper);
        long totalPages = (long) Math.ceil((double) totalNewsCount / pageSize);
        System.err.println("总新闻数：" + totalNewsCount + "，总页数：" + totalPages + "，每页条数：" + pageSize);

        // ========== 分页查询所有新闻==========
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<News>()
                .orderByDesc(News::getPublishTime);
        Page<News> newsPage = new Page<>(pageNum, pageSize);
        Page<News> resultPage = newsMapper.selectPage(newsPage, queryWrapper);

        // 总新闻数（未过滤已读）
        long total = resultPage.getTotal();
        // 当前页查询到的新闻列表
        List<News> currentPageNews = resultPage.getRecords();

        // ========== 调试日志（关键） ==========
        List<String> currentPageNewsIds = currentPageNews.stream()
                .map(news -> String.valueOf(news.getId()))
                .toList();
        System.err.println("当前页页码：" + pageNum + "，当前页新闻ID列表：" + currentPageNewsIds);

        // ==========  过滤已读新闻 ==========
        Set<String> browsedNewsIds = operateBrowsedNewsService.getBrowsedNewsIds(sessionId);
        System.err.println("已读新闻ID列表（数量：" + browsedNewsIds.size() + "）：" + browsedNewsIds);

        // 过滤当前页的已读新闻
        List<News> unreadNews = NewsFilterUtil.filterUnreadNews(currentPageNews, browsedNewsIds);
        System.err.println("当前页过滤后未读新闻数：" + (unreadNews == null ? 0 : unreadNews.size()));

        // ==========  补充未读新闻逻辑==========
        // 当前页有未读但数量不足 → 补充后续页
        if (CollectionUtils.isNotEmpty(unreadNews) && unreadNews.size() < pageSize) {
            int lackNum = pageSize - unreadNews.size();
            Page<News> supplementPage = new Page<>(pageNum + 1, lackNum);
            Page<News> supplementResult = newsMapper.selectPage(supplementPage, queryWrapper);
            List<News> supplementNews = NewsFilterUtil.filterUnreadNews(supplementResult.getRecords(), browsedNewsIds);
            if (CollectionUtils.isNotEmpty(supplementNews)) {
                unreadNews.addAll(supplementNews);
                if (unreadNews.size() > pageSize) {
                    unreadNews = unreadNews.subList(0, pageSize);
                }
            }
            System.err.println("补充后未读新闻数：" + unreadNews.size());
        }
        // 当前页过滤后为空 → 主动查询后续页找未读（核心优化）
        else if (CollectionUtils.isEmpty(unreadNews)) {
            System.err.println("当前页全为已读，开始查询后续页找未读新闻...");
            int currentTryPage = pageNum + 1;
            // 最大可尝试的页码（取总页数和pageNum+5的最小值）
            int maxTryPageNum = (int) Math.min(pageNum + maxTryPages, totalPages);

            while (currentTryPage <= maxTryPageNum) {
                System.err.println("尝试查询页码：" + currentTryPage + "（总页数：" + totalPages + "）");
                Page<News> tryPage = new Page<>(currentTryPage, pageSize);
                Page<News> tryPageResult = newsMapper.selectPage(tryPage, queryWrapper);
                List<News> tryPageNews = tryPageResult.getRecords();

                // 后续页无数据，直接退出
                if (CollectionUtils.isEmpty(tryPageNews)) {
                    System.err.println("页码：" + currentTryPage + " 无数据，停止查询");
                    break;
                }

                // 打印尝试页的新闻ID
                List<String> tryPageNewsIds = tryPageNews.stream()
                        .map(news -> String.valueOf(news.getId()))
                        .toList();
                System.err.println("页码：" + currentTryPage + " 新闻ID列表：" + tryPageNewsIds);

                // 过滤该页的已读新闻
                List<News> tryPageUnread = NewsFilterUtil.filterUnreadNews(tryPageNews, browsedNewsIds);
                System.err.println("页码：" + currentTryPage + " 过滤后未读新闻数：" + tryPageUnread.size());

                if (CollectionUtils.isNotEmpty(tryPageUnread)) {
                    unreadNews = tryPageUnread;
                    // 只取pageSize条
                    if (unreadNews.size() > pageSize) {
                        unreadNews = unreadNews.subList(0, pageSize);
                    }
                    System.err.println("在页码：" + currentTryPage + " 找到未读新闻，数量：" + unreadNews.size());
                    break; // 找到后退出循环
                }

                currentTryPage++;
            }
        }

        // ==========  保存本次浏览的新闻ID（保留原有逻辑） ==========
        if (CollectionUtils.isNotEmpty(unreadNews)) {
            operateBrowsedNewsService.saveBrowsedNewsIds(sessionId, unreadNews);
            System.err.println("本次保存的已读新闻ID：" + unreadNews.stream().map(n -> String.valueOf(n.getId())).toList());
        }

        // ==========  转换为DTO并封装分页结果 ==========
        List<NewsItemDTO> newsItemDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(unreadNews)) {
            newsItemDTOS = newsDtoMapper.toNewsItemDTOList((ArrayList<News>) unreadNews);
        }

        // 返回分页数据：列表 + 总条数 + 是否还有更多未读（新增）
        Map<String, Object> result = new HashMap<>();
        result.put("records", newsItemDTOS); // 当前页数据
        // 标记是否还有更多未读（前端可据此隐藏加载按钮/停止滚动加载）
        result.put("hasMoreResp", CollectionUtils.isNotEmpty(unreadNews));
        // 提示信息（便于前端展示）
        result.put("message", CollectionUtils.isEmpty(unreadNews) ? "暂无更多未读新闻" : "获取未读新闻成功");

        System.err.println("最终返回结果：" + result);
        return result;
    }
}