package com.news.backendproject.service;

import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import com.news.backendproject.dto.SendEmailCaptchaDTO;
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

    //用于注册时发送邮箱验证码
    public ApiResponse<GeneralDataResponse> sendRegister(SendEmailCaptchaDTO dto, String redisKey) {

        String emailCaptchaGenerated=RadomCaptchaUtil.generate8DigitCaptcha();

        boolean result= emailGenerateService.sendCaptcha(dto.getToEmail(),emailCaptchaGenerated,dto.getOperationType());
        if (result){
            //发送成功后存储验证码到redis
            stringRedisTemplate.opsForValue().set(
                    redisKey,
                    emailCaptchaGenerated,
                    300,
                    TimeUnit.SECONDS
            );
            return new ApiResponse<>(200,"发送成功",new GeneralDataResponse(true,null));
        }
        return new ApiResponse<>(500,"邮箱验证码发送失败",new GeneralDataResponse(false,null));
    }
    //用于忘记密码时发送邮箱验证码
    public ApiResponse<GeneralDataResponse> sendForgotPwd(SendEmailCaptchaDTO dto, String redisKey){
        return new ApiResponse<>(500,"测试sendForgotPwd函数成功",new GeneralDataResponse(true,null));
    }
}
