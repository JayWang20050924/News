package com.news.backendproject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@ToString(of = {"id","username", "password","email","gender","address","birthday"})
public class User {
    private int id;
    private String username;
    //确保向前端传输user对象时不包含密码
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private String gender;
    private String address;
    private LocalDate birthday;
}
