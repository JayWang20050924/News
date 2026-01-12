package com.news.backendproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 新闻表实体类
 */
@Data
@TableName("news") // MyBatis-Plus：指定对应数据库表名（替换为实际表名）
public class News {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO) // 主键字段映射，type=AUTO适配MySQL自增主键
    private Long id;

    /**
     * 新闻标题
     */
    @TableField("title") // 普通字段映射（字段名与属性名一致时可省略）
    private String title;

    /**
     * 新闻摘要
     */
    @TableField("summary")
    private String summary;

    /**
     * 新闻正文
     */
    @TableField("content")
    private String content;

    /**
     * 新闻分类
     */
    @TableField("category")
    private String category;

    /**
     * 分类对应颜色
     */
    @TableField("category_color")
    private String categoryColor;

    /**
     * 封面图片URL
     */
    @TableField("cover")
    private String cover;

    /**
     * 新闻作者
     */
    @TableField("author")
    private String author;

    /**
     * 发布时间
     */
    @TableField("publish_time")
    private LocalDateTime publishTime;

    /**
     * 阅读量
     */
    @TableField("view_count")
    private Integer viewCount;

    /**
     * 点赞数
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 状态：1=已发布，0=未发布，-1=软删除
     */
    @TableField("status")
    private Integer status;


    /**
     * 头条状态：1=头条，0=普通新闻
     */
    @TableField("headline_status")
    private Integer headlineStatus;
    /**
     * 创建时间（MP自动填充，需配置填充处理器）
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间（MP自动填充）
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
