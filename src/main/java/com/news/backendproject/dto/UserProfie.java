package com.news.backendproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data

public class UserProfie {
    private long id;
    private String username;
    private String email;
    private String gender;
    private String address;
    // @JsonFormat日期格式化逻辑不变
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthday;
}
