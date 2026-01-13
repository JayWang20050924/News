package com.news.backendproject.service.news;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.news.HomepageNewsResponseDTO;
import com.news.backendproject.dto.news.TopNewsDTO;
import com.news.backendproject.entity.News;
import com.news.backendproject.mapstruct.NewsDtoMapper;
import com.news.backendproject.mapper.NewsMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class HomepageNewsService {
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private NewsDtoMapper newsDtoMapper;
    public ApiResponse<HomepageNewsResponseDTO> getHomepageNews() {
        HomepageNewsResponseDTO homepageDto = new HomepageNewsResponseDTO();
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<News>().eq(News::getHeadlineStatus, 1);
        News news = newsMapper.selectOne(queryWrapper);
        TopNewsDTO topNewsDTO = newsDtoMapper.toTopNewsDTO(news);
        System.err.println("topNews:"+topNewsDTO);
        homepageDto.setTopNews(topNewsDTO);
        System.err.println("homepageDto:"+homepageDto);
        ApiResponse<HomepageNewsResponseDTO> homepageApiResponse = new ApiResponse<>(200, "获取新闻首页信息成功", homepageDto);
        return homepageApiResponse;
    }
}
