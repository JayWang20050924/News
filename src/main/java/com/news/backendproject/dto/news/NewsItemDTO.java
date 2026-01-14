package com.news.backendproject.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.news.backendproject.dto.NewsGeneralDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 新闻条目通用DTO
 * 适配前端hotNews、latestNews数据结构
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class NewsItemDTO extends NewsGeneralDTO {
}