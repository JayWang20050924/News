package com.news.backendproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.news.backendproject.entity.News;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewsMapper extends BaseMapper<News> {
}
