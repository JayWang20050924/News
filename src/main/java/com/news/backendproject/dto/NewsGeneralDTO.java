package com.news.backendproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@SuperBuilder
public class NewsGeneralDTO {
    protected Integer id;
    protected String title;
    protected String summary;
    protected ArrayList<String> content;
    protected String category;
    protected String categoryColor;
    protected String cover;
    protected String author;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime publishTime;
    protected Integer viewCount;
    protected Integer likeCount;
    protected Integer commentCount;
    protected Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime updatedAt;
    protected Integer headlineStatus;
}