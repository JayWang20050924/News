package com.news.backendproject.dto.news;

import com.news.backendproject.dto.AbstractNewsGeneralDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 热门排行新闻DTO
 * 对应前端rankedNews数据结构
 * rank字段：通过SQL计算.非数据库直接存储
 */
@Data
@EqualsAndHashCode(callSuper = true) // 确保equals/hashCode包含基类字段
@NoArgsConstructor // 保留无参构造器（框架反序列化必需）
@SuperBuilder // 继承基类构建器，支持包含rank的对象构建
public class NewsRankedListDto extends AbstractNewsGeneralDTO {
    /** 排名（1-10）：专属扩展字段，通过SQL计算得到 */
    private int rank;
}