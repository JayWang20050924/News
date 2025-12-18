package com.news.backendproject.verify;

import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GenaralDataResponse;
import org.springframework.stereotype.Service;
//todo:完成邮箱验证码的读取与验证
@Service
public class EmailCaptchaVerification {
    public ApiResponse<GenaralDataResponse> verify(String email, String captcha, String redisKey) {
        return new ApiResponse<>(200,"邮箱验证码正确",new GenaralDataResponse(true,null));
    }
}
