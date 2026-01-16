package com.news.backendproject.dto.news;

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
    private TopNewsDTO topNews;
    private List<NewsItemDTO> hotNews;
    private List<NewsItemDTO> latestNews;

    /** 热门排行 */
    private List<NewsRankedListDto> rankedNews;

    /** 赞助商列表 */
    private List<SponsorDTO> sponsors;
}