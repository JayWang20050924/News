package com.news.backendproject.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.news.backendproject.dto.NewsGeneralDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 头条新闻DTO：适配前端topNews数据结构
 * 继承基类，排除冗余字段，id/likes转为字符串，time直接返回LocalDateTime格式化值
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class TopNewsDTO extends NewsGeneralDTO {
}