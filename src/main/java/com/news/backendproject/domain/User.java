package com.news.backendproject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data // 核心注解：自动生成所有字段的getter、setter、equals、hashCode
@ToString(of = {"username", "password", "gender", "address","birthday"}) // 自定义toString，与原逻辑保持一致
public class User {
    private long id;
    private String username;

    // @JsonIgnore保留：字段上的注解会被Lombok生成的getter继承，确保JSON序列化时忽略密码
    @JsonIgnore
    private String password;

    private String gender;
    private String address;

    // @JsonFormat保留：日期格式化逻辑不变
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthday;
}