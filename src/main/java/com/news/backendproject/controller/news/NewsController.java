package com.news.backendproject.controller.news;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.news.backendproject.annotation.AccessRestriction;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.news.DetailedNewsDto;
import com.news.backendproject.dto.news.HomepageNewsResponseDTO;
import com.news.backendproject.po.News;
import com.news.backendproject.mapper.NewsMapper;
import com.news.backendproject.mapstruct.NewsDtoMapper;
import com.news.backendproject.service.news.HomepageNewsService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @AccessRestriction(limit = 15, message = "请稍后再试", limitKey = false)
    @GetMapping("/homepage")
    public ApiResponse<HomepageNewsResponseDTO> homepage() {
        return homepageNewsService.getHomepageNews();
    }

    @AccessRestriction(message = "请稍后再试", limitKey = false)
    @PostMapping("/detailed")
    public ApiResponse<DetailedNewsDto> detailNews(@RequestParam Integer newsId) {
        System.err.println("id:"+newsId);
        News news = newsMapper.selectOne(new LambdaQueryWrapper<News>().eq(News::getId, newsId));
        ApiResponse<DetailedNewsDto> newsApiResponse = new ApiResponse<>();
        newsApiResponse.setCode(200);
        newsApiResponse.setMsg("success");
        System.out.println(news);
        newsApiResponse.setData(newsDtoMapper.toDetailedNewsDto(news));
        return newsApiResponse;
    }
}
