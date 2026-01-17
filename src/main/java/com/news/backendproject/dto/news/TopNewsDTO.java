package com.news.backendproject.dto.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 头条新闻DTO：适配前端topNews数据结构
 * 继承基类，通过重写不需要的字段并添加@JsonIgnore排除冗余返回
 * 核心：复用父类需要的字段，仅忽略不需要的字段
 */
@JsonIgnoreProperties({
        "content",        // 新闻正文
        "author",         // 新闻作者
        "viewCount",      // 阅读量
        "commentCount",   // 评论数
        "status",         // 新闻状态
        "createdAt",      // 创建时间
        "updatedAt",      // 更新时间
        "headlineStatus"  // 头条状态
})
@Data
@EqualsAndHashCode(callSuper = true) // 必须加callSuper=true，确保父类字段参与计算
@NoArgsConstructor
@SuperBuilder
public class TopNewsDTO extends NewsGeneralDTO {
}