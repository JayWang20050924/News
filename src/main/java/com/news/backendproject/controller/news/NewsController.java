package com.news.backendproject.controller.news;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.annotation.JsonView;
import com.news.backendproject.annotation.AccessRestriction;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.news.HomepageNewsResponseDTO;
import com.news.backendproject.dto.news.NewsView;
import com.news.backendproject.entity.News;
import com.news.backendproject.mapper.NewsMapper;
import com.news.backendproject.service.news.HomepageNewsService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/news")
public class NewsController {
    @Resource
    private NewsMapper newsMapper;
    private final HomepageNewsService homepageNewsService;


    @AccessRestriction(limit = 15, message = "请稍后再试", limitKey = false)
    @GetMapping("/homepage")
    public ApiResponse<HomepageNewsResponseDTO> homepage() {
        ApiResponse<HomepageNewsResponseDTO> homepageNews = homepageNewsService.getHomepageNews();
        System.err.println(homepageNews);
        return homepageNews;
    }
}
