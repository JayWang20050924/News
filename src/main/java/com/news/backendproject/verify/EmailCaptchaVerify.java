package com.news.backendproject.verify;

import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailCaptchaVerify {
    private final StringRedisTemplate stringRedisTemplate;
    public ApiResponse<GeneralDataResponse> verify(String emailCaptcha, String redisKey) {
        String redisValue=null;
        redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        System.err.println("验证码来源->"+redisKey);
        System.err.println("用户输入的验证码->"+redisValue);
        if (redisValue == null) {
            return new ApiResponse<>(400, "请重新获取验证码", new GeneralDataResponse(false, null));
        }

        if(emailCaptcha.equals(redisValue)) {
            stringRedisTemplate.delete(redisKey);
            System.err.println("验证码-正确>"+redisValue);
            return new ApiResponse<>(200,"邮箱验证码正确",new GeneralDataResponse(true,null));
        }else {
            System.err.println("验证码-错误>"+redisValue);
            return new ApiResponse<>(400,"邮箱验证码错误",new GeneralDataResponse(true,null));
        }
    }
}
