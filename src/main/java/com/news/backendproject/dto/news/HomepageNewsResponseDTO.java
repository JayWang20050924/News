package com.news.backendproject.dto.news;

import com.fasterxml.jackson.annotation.JsonView;
import com.news.backendproject.dto.sponsor.SponsorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 首页新闻返回总DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomepageNewsResponseDTO {
    /** 头条新闻（仅序列化TopNewsView字段） */
    @JsonView(NewsView.TopNewsView.class)
    private TopNewsDTO topNews;

    /** 热门新闻列表（序列化HomepageView字段） */
    @JsonView(NewsView.HomepageView.class)
    private List<NewsItemDTO> hotNews;

    /** 最新资讯列表（序列化HomepageView字段） */
    @JsonView(NewsView.HomepageView.class)
    private List<NewsItemDTO> latestNews;

    /** 热门排行列表 */
    private List<NewsRankedListDto> rankedNews;

    /** 赞助商列表 */
    private List<SponsorDTO> sponsors;
}