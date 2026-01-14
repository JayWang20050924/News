package com.news.backendproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.news.backendproject.dto.news.NewsView;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
public class NewsGeneralDTO {
    /** 新闻ID  */
    @JsonView(NewsView.FrontBaseView.class)
    protected Integer id;

    /** 新闻标题 */
    @JsonView(NewsView.FrontBaseView.class)
    protected String title;

    /** 新闻摘要*/
    @JsonView(NewsView.FrontBaseView.class)
    protected String summary;

    /** 新闻正文 */
    @JsonView(NewsView.NewsDetailView.class)
    protected String content;

    /** 新闻分类  */
    @JsonView(NewsView.FrontBaseView.class)
    protected String category;

    /** 分类标签颜色 */
    @JsonView(NewsView.FrontBaseView.class)
    protected String categoryColor;

    /** 封面地址  */
    @JsonView(NewsView.FrontBaseView.class)
    protected String cover;

    /** 新闻作者  */
    @JsonView(NewsView.NewsDetailView.class)
    protected String author;

    /** 发布时间 */
    @JsonView(NewsView.FrontBaseView.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime publishTime;

    /** 阅读量  */
    @JsonView({NewsView.NewsItemView.class, NewsView.NewsDetailView.class})
    protected Integer viewCount;

    /** 点赞数 */
    @JsonView({NewsView.NewsDetailView.class,NewsView.TopNewsView.class})
    protected Integer likeCount;

    /** 评论数  */
    @JsonView({NewsView.NewsItemView.class, NewsView.NewsDetailView.class})
    protected Integer commentCount;

    /** 状态  */
    @JsonView(NewsView.AdminFullView.class)
    protected Integer status;

    /** 创建时间  */
    @JsonView(NewsView.AdminFullView.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime createdAt;

    /** 更新时间 */
    @JsonView(NewsView.AdminFullView.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime updatedAt;

    /** 头条状态 */
    @JsonView(NewsView.AdminFullView.class)
    protected Integer headlineStatus;
}