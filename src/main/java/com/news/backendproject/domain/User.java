package com.news.backendproject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data //自动生成所有字段的getter、setter、equals、hashCode
@NoArgsConstructor
@ToString(of = {"username", "password","confirmPassword","email",})
public class User {
    private String username;
    //确保向前端传输user对象时不包含密码
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;
    private String email;
    private String emailCaptcha;
    private String operationType;
}