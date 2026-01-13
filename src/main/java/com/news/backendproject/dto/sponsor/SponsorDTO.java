package com.news.backendproject.dto.sponsor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 赞助商/合作伙伴DTO
 * 对应前端sponsors数据结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SponsorDTO {
    /** 赞助商ID */
    private String id;
    /** 赞助商名称 */
    private String name;
    /** 赞助商链接 */
    private String link;
    /** 赞助商封面图片地址 */
    private String cover;
}
