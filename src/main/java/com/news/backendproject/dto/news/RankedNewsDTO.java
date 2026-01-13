package com.news.backendproject.dto.news;

import com.news.backendproject.dto.AbstractNewsGeneralDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 热门排行新闻DTO
 * 对应前端rankedNews数据结构
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RankedNewsDTO extends AbstractNewsGeneralDTO {
    /** 新闻ID */
    private int id;
    /** 新闻标题 */
    private String title;
    /** 浏览量 */
    private int viewCount;
    /** 排名（1-10） */
    private int rank;
}
