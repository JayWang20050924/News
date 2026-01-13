package com.news.backendproject.mapper;

import com.news.backendproject.dto.AbstractNewsGeneralDTO;
import com.news.backendproject.dto.news.NewsItemDTO; // 示例子类DTO
import com.news.backendproject.dto.news.NewsRankedListDto;  // 示例子类DTO
import com.news.backendproject.dto.news.TopNewsDTO;
import com.news.backendproject.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * News实体与各News DTO的映射器（排除HomepageNewsResponseDTO）
 * componentModel = "spring"生成的实现类交给Spring管理，可直接@Autowired注入
 */
@Mapper(componentModel = "spring")
public interface NewsDtoMapper {
    /**
     * 基础映射：News -> 抽象基类DTO（所有子类复用此映射逻辑）
     * 字段名/类型完全匹配，无需额外@Mapping注解
     */
    AbstractNewsGeneralDTO toAbstractNewsDTO(News news);

    // ------------------------ 各子类DTO的映射方法 ------------------------
    TopNewsDTO toTopNewsDTO(News news);

    NewsItemDTO toNewsItemDTO(News news);

    @Mapping(target = "rank", ignore = true)
    NewsRankedListDto toNewsRankedListDto(News news);
    default NewsRankedListDto toNewsRankedListDto(News news, int rank) {
        NewsRankedListDto dto = toNewsRankedListDto(news);
        dto.setRank(rank);
        return dto;
    }




    //批量映射
    List<TopNewsDTO> toTopNewsDTOList(List<News> newsList);

    List<NewsItemDTO> toNewsItemDTOList(List<News> newsList);

    List<NewsRankedListDto> toNewsRankedListDtoList(List<News> newsList);


    // 有额外字段的DTO
    // @Mapping(source = "实体字段名", target = "DTO额外字段名") // 字段不匹配时添加
    // SpecialNewsDTO toSpecialNewsDTO(News news);
}