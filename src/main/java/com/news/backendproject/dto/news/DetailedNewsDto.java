package com.news.backendproject.dto.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 新闻详情页DTO
 * 适配前端新闻详情页数据结构
 */
@JsonIgnoreProperties({
        "status",         // 新闻状态
        "createdAt",      // 创建时间
        "updatedAt",      // 更新时间
        "headlineStatus"  // 头条状态
})
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class DetailedNewsDto extends NewsGeneralDTO {
}