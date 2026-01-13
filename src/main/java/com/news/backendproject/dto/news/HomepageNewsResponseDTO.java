package com.news.backendproject.dto.news;

import com.news.backendproject.dto.sponsor.SponsorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 首页新闻返回总DTO
 * 对应前端getHomepageNews接口的返回数据结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomepageNewsResponseDTO {
    /** 头条新闻 */
    private TopNewsDTO topNews;
    /** 热门新闻列表 */
    private List<NewsItemDTO> hotNews;
    /** 最新资讯列表 */
    private List<NewsItemDTO> latestNews;
    /** 热门排行列表 */
    private List<RankedNewsDTO> rankedNews;
    /** 赞助商列表 */
    private List<SponsorDTO> sponsors;
}