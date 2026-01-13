package com.news.backendproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class NewsGeneralDTO {
    // 所有新闻DTO都可能用到的公共字段
    /** 新闻ID */
    protected Integer id;
    /** 新闻标题 */
    protected String title;
    /** 新闻摘要 */
    protected String summary;
    /** 新闻正文 */
    protected String content;
    /** 新闻分类 */
    protected String category;
    /** 新闻分类标签背景色 */
    protected String categoryColor;
    /** 封面图片地址 */
    protected String cover;
    /** 新闻作者 */
    protected String author;
    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime publishTime;
    /** 阅读量 */
    protected Integer viewCount;
    /** 点赞数 */
    protected Integer likeCount;
    /** 评论数 */
    protected Integer commentCount;
    /** 状态：1=已发布，0=未发布，-1=软删除 */
    protected Integer status;
    /** 创建时间 */
    protected LocalDateTime createdAt;
    /** 更新时间 */
    protected LocalDateTime updatedAt;
    /** 头条状态：1=头条，0=普通新闻 */
    protected Integer headlineStatus;
}