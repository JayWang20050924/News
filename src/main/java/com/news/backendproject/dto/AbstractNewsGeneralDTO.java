package com.news.backendproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 新闻DTO抽象基类：提取所有新闻DTO的公共字段
 */
@Data
@NoArgsConstructor
@SuperBuilder // 支持子类构建器（Lombok注解，需配合子类@Builder）
public abstract class AbstractNewsGeneralDTO {
    // 所有新闻DTO都可能用到的公共字段
    protected int id;
    protected String title;
    protected String summary;
    protected String content;
    protected String category;
    protected String categoryColor;
    protected String cover;
    protected String author;
    protected LocalDateTime publishTime;
    protected int viewCount;
    protected int likeCount;
    protected int commentCount;
    protected int status;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected int headlineStatus;
}