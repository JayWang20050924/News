package com.news.backendproject.service.news;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.news.backendproject.dto.news.HomepageNewsResponseDTO;
import com.news.backendproject.dto.news.TopNewsDTO;
import com.news.backendproject.entity.News;
import com.news.backendproject.mapper.NewsMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class HomepageNewsService {
    @Resource
    private NewsMapper newsMapper;
    public HomepageNewsResponseDTO getHomepageNews() {
        HomepageNewsResponseDTO homepageDto = new HomepageNewsResponseDTO();
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<News>().eq(News::getHeadlineStatus, 1);
        News news = newsMapper.selectOne(queryWrapper);
        homepageDto.setTopNews(new TopNewsDTO());
        return null;
    }
}
