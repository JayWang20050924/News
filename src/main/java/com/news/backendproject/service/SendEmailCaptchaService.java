package com.news.backendproject.service;

import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import com.news.backendproject.utils.RadomCaptchaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SendEmailCaptchaService {
    private final EmailGenerateService emailGenerateService;
    private final StringRedisTemplate stringRedisTemplate;

    public ApiResponse<GeneralDataResponse> sendRegister(String toEmail, String redisKey,String operationType) {
        String emailCaptcha=RadomCaptchaUtil.generate8DigitCaptcha();

        boolean result= emailGenerateService.sendCaptcha(toEmail,emailCaptcha,operationType);
        if (result){
            //发送成功后存储验证码到redis
            stringRedisTemplate.opsForValue().set(
                    redisKey,
                    emailCaptcha,
                    300,
                    TimeUnit.SECONDS
            );
            return new ApiResponse<>(200,"发送成功",new GeneralDataResponse(true,null));
        }
        return new ApiResponse<>(500,"邮箱验证码发送失败",new GeneralDataResponse(false,null));
    }
    public ApiResponse<GeneralDataResponse> sendForForgotPwd(String toEmail, String redisKey,String operationType){
        return new ApiResponse<>(500,"邮箱验证码发送失败",new GeneralDataResponse(false,null));
    }
}
