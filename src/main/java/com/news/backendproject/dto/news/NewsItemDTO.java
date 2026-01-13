package com.news.backendproject.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.news.backendproject.dto.NewsGeneralDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 新闻条目通用DTO
 * 适配前端hotNews、latestNews数据结构
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = false)
public class NewsItemDTO extends NewsGeneralDTO {
    /** 新闻ID */
    private Integer id;
    /** 新闻标题 */
    private String title;
    /** 新闻摘要 */
    private String summary;
    /** 新闻分类 */
    private String category;
    /** 分类标签颜色（如：blue、green） */
    private String categoryColor;
    /** 封面图片地址 */
    private String cover;
    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time;
    /** 浏览量 */
    private Integer viewCount;
    /** 评论数- 最新新闻专属 */
    private Integer commentCount;
}