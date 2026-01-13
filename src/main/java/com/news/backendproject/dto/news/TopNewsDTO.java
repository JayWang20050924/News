package com.news.backendproject.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.news.backendproject.dto.AbstractNewsGeneralDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 头条新闻DTO
 * 对应前端topNews数据结构
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
//兼容抽象基础类
@SuperBuilder
public class TopNewsDTO extends AbstractNewsGeneralDTO {
    /** 新闻ID */
    private int  id;
    /** 新闻标题 */
    private String title;
    /** 新闻摘要 */
    private String summary;
    /** 新闻分类 */
    private String category;
    /** 分类标签颜色 */
    private String categoryColor;
    /** 封面图片地址 */
    private String cover;
    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time;
    /** 点赞数 */
    private int likes;
}
