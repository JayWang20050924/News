package com.news.backendproject.dto.news;

import lombok.*;

/**
 * 热门排行新闻DTO
 * 对应前端rankedNews数据结构
 * 不继承基础抽象类
 */
@Data
public class NewsRankedListDto {
    private Integer id;
    private String title;
    private Integer viewCount;
    private Integer rank;
}