package com.news.backendproject.service;

import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GeneralDataResponse;
import com.news.backendproject.utils.RadomCaptchaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//todo:完成邮箱验证码的发送与存储
@Service
@RequiredArgsConstructor
public class SendEmailCaptchaService {
    private final EmailGenerateService emailGenerateService;
    private final StringRedisTemplate stringRedisTemplate;

    public ApiResponse<GeneralDataResponse> send(String toEmail, String redisKey,String operationType) {
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
}
