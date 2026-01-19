package com.news.backendproject.controller.news;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.news.backendproject.annotation.AccessRestriction;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.news.DetailedNewsDto;
import com.news.backendproject.dto.news.HomepageNewsResponseDTO;
import com.news.backendproject.dto.news.NewsItemDTO;
import com.news.backendproject.po.News;
import com.news.backendproject.mapper.NewsMapper;
import com.news.backendproject.mapstruct.NewsDtoMapper;
import com.news.backendproject.service.news.BrowseMoreNewsService;
import com.news.backendproject.service.news.HomepageNewsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/news")
public class NewsController {
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private NewsDtoMapper newsDtoMapper;

    private final HomepageNewsService homepageNewsService;
    private final BrowseMoreNewsService browseMoreNewsService;

    @AccessRestriction(limit = 15, message = "请稍后再试", limitKey = false)
    @GetMapping("/homepage")
    public ApiResponse<HomepageNewsResponseDTO> homepage() {
        return homepageNewsService.getHomepageNews();
    }

    @AccessRestriction(message = "请稍后再试", limitKey = false)
    @PostMapping("/detailed")
    public ApiResponse<DetailedNewsDto> detailNews(@RequestParam Integer newsId) {
        News news = newsMapper.selectOne(new LambdaQueryWrapper<News>().eq(News::getId, newsId));
        ApiResponse<DetailedNewsDto> newsApiResponse = new ApiResponse<>();
        newsApiResponse.setCode(200);
        newsApiResponse.setMsg("success");
        newsApiResponse.setData(newsDtoMapper.toDetailedNewsDto(news));
        return newsApiResponse;
    }

    @AccessRestriction(message = "请稍后再试", limitKey = false)
    @PostMapping("/browseMore")
        public ApiResponse<Map<String, Object>> browseMore(
                @RequestParam String pageNum,
                @RequestParam String pageSize,
                HttpServletRequest request) {
        return new ApiResponse<>(200, "success", browseMoreNewsService.getBrowseMoreNews(request, pageNum, pageSize));
    }
}
