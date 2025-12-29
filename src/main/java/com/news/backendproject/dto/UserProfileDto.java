package com.news.backendproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data

public class UserProfileDto {
    //限制只能在序列化时(向前端写入)被赋值
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String username;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String email;
    private String gender;
    private String address;
    // @JsonFormat日期格式化
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthday;
}
