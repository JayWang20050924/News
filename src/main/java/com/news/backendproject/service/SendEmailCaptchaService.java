package com.news.backendproject.service;

import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GenaralDataResponse;
import org.springframework.stereotype.Service;

//todo:完成邮箱验证码的发送与存储
@Service
public class SendEmailCaptchaService {
    public ApiResponse<GenaralDataResponse> send(String email, String redisKey) {
        return new ApiResponse<>(200,"发送成功",new GenaralDataResponse(true,null));
    }
}
