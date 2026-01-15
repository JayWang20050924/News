package com.news.backendproject.mapstruct;

import com.news.backendproject.dto.NewsGeneralDTO;
import com.news.backendproject.dto.news.NewsItemDTO;
import com.news.backendproject.dto.news.NewsRankedListDto;
import com.news.backendproject.dto.news.TopNewsDTO;
import com.news.backendproject.entity.News;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * News实体与各News DTO的映射器（MapStruct专用，非MyBatis查询Mapper）
 * 前端字段已调整，无需额外字段映射，仅保留基础匹配逻辑
 * componentModel = "spring"：生成的实现类交给Spring管理，可@Autowired注入
 */
@Mapper(componentModel = "spring")
public interface NewsDtoMapper {
    // ------------------------ 基础映射：News ->抽象基类DTO ------------------------
    /**
     * 基础映射：字段名/类型完全匹配，无需@Mapping注解
     */
    NewsGeneralDTO toNewsGeneralDTO (News news);

    // ------------------------ 子类DTO映射（无额外映射，直接匹配） ------------------------
    /**
     * TopNewsDTO映射：前端字段已匹配，无需任何映射规则
     */
    TopNewsDTO toTopNewsDTO(News news);

    /**
     * NewsItemDTO映射：前端字段已匹配，无需任何映射规则
     */
    NewsItemDTO toNewsItemDTO(News news);
    /**
     * NewsRankedListDto映射：前端字段已匹配，无需任何映射规则
     */
    ArrayList<NewsRankedListDto> toNewsRankedListDtoList(List<News> newsList);
    // ------------------------ 批量映射 ------------------------
    ArrayList<NewsItemDTO> toNewsItemDTOList(ArrayList<News> newsList);
    NewsRankedListDto toNewsRankedListDto(News news);

}