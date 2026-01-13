package com.news.backendproject.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.news.backendproject.dto.AbstractNewsGeneralDTO;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 头条新闻DTO
 * 对应前端topNews数据结构
 */
@EqualsAndHashCode(callSuper = true) // 保留，确保equals/hashCode包含基类字段
@NoArgsConstructor // 仅保留无参构造器（框架反序列化必需）
@SuperBuilder // 继承基类的构建器，满足对象构建需求（替代@AllArgsConstructor）
public class TopNewsDTO extends AbstractNewsGeneralDTO {
    /**
     * 发布时间：复用基类publishTime字段，映射为前端的time字段
     */
    @Override
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("time")
    public LocalDateTime getPublishTime() {
        return super.getPublishTime();
    }

    /**
     * 反序列化使用
     */
    @Override
    @JsonProperty("time")
    public void setPublishTime(LocalDateTime publishTime) {
        super.setPublishTime(publishTime);
    }
    //todo:排除前端不需要的字段 @JsonIgnore
}