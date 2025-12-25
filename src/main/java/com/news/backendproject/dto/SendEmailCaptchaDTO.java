package com.news.backendproject.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(of = {"username","toEmail","operationType"})
public class SendEmailCaptchaDTO implements ExistenceVerifyDto{
    private String username;

    // 邮箱非空 + 格式验证（仅保留当前接口需要的验证）
    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "邮箱格式错误")
    private String toEmail;

    @NotBlank(message = "非法请求")
    private String operationType;


    //显式重写ExistenceVerifyDto,用于用户信息存在性判断
    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public String getEmail() {
        return this.toEmail;
    }
}
