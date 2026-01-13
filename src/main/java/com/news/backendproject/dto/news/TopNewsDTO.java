package com.news.backendproject.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.news.backendproject.dto.NewsGeneralDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 头条新闻DTO：适配前端topNews数据结构
 * 继承基类，排除冗余字段，id/likes转为字符串，time直接返回LocalDateTime格式化值
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class TopNewsDTO extends NewsGeneralDTO {

    // 手动编写toString()，主动获取父类字段
    @Override
    public String toString() {
        return "TopNewsDTO{" +
                "id=" + super.getId() +
                ", title='" + super.getTitle() + '\'' +
                ", summary='" + super.getSummary() + '\'' +
                ", category='" + super.getCategory() + '\'' +
                ", categoryColor='" + super.getCategoryColor() + '\'' +
                ", cover='" + super.getCover() + '\'' +
                ", publishTime=" + (super.getPublishTime() != null ? super.getPublishTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null) +
                ", likeCount=" + super.getLikeCount() +
                '}';
    }
    // ------------------------ 核心字段适配（前端需要的字段） ------------------------
    /**
     * 发布时间：基类publishTime → 前端time，直接返回LocalDateTime格式化值
     * 格式：yyyy-MM-dd HH:mm:ss（可根据前端需求调整）
     */
    @Override
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // 格式化LocalDateTime
    public LocalDateTime getPublishTime() {
        return super.getPublishTime();
    }
    // ------------------------ 排除所有前端不需要的字段 ------------------------
    /** 排除正文内容 */
    @Override
    @JsonIgnore
    public String getContent() {
        return super.getContent();
    }

    /** 排除作者字段 */
    @Override
    @JsonIgnore
    public String getAuthor() {
        return super.getAuthor();
    }

    /** 排除后端状态字段 */
    @Override
    @JsonIgnore
    public Integer getStatus() {
        return super.getStatus();
    }

    /** 排除创建时间 */
    @Override
    @JsonIgnore
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    /** 排除更新时间 */
    @Override
    @JsonIgnore
    public LocalDateTime getUpdatedAt() {
        return super.getUpdatedAt();
    }

    /** 排除头条状态标识 */
    @Override
    @JsonIgnore
    public Integer getHeadlineStatus() {
        return super.getHeadlineStatus();
    }

    /** 排除浏览量 */
    @Override
    @JsonIgnore
    public Integer getViewCount() {
        return super.getViewCount();
    }

    /** 排除评论数 */
    @Override
    @JsonIgnore
    public Integer getCommentCount() {
        return super.getCommentCount();
    }
}