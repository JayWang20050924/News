package com.news.backendproject.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data //自动生成所有字段的getter、setter、equals、hashCode
@NoArgsConstructor
@ToString(of = {"username", "passwordReset","confirmPwdReset","email","emailCaptcha","operationType"})
public class UserForgotDto implements ExistenceVerifyDto {
    @Pattern(regexp = "^[A-Za-z0-9]{8,20}$", message = "用户名需为8-20位数字和字母组合")
    private String username;

    @Pattern(regexp = "^[A-Za-z0-9]{8,20}$", message = "密码需为8-20位数字和字母组合")
    //确保向前端传输user对象时不包含密码
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordReset;

    @Pattern(regexp = "^[A-Za-z0-9]{8,20}$", message = "密码需为8-20位数字和字母组合")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPwdReset;

    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "邮箱格式不正确")
    private String email;

    @Pattern(regexp = "^[0-9]{8}$", message = "邮箱验证码需为8位数字组合")
    private String emailCaptcha;

    @NotBlank(message = "非法请求")
    private String operationType;

    //显式重写ExistenceVerifyDto,用于用户信息存在性判断
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getEmail() {
        return this.email;
    }
}