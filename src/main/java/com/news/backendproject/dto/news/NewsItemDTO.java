package com.news.backendproject.dto.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.news.backendproject.dto.NewsGeneralDTO;
import lombok.EqualsAndHashCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 新闻条目通用DTO
 * 适配前端hotNews、latestNews数据结构
 */
@JsonIgnoreProperties({
        "content",        // 新闻正文
        "author",         // 新闻作者
        "createdAt",      // 创建时间
        "updatedAt",      // 更新时间
        "status",         // 新闻状态
        "headlineStatus"  // 头条状态
})
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class NewsItemDTO extends NewsGeneralDTO {
}