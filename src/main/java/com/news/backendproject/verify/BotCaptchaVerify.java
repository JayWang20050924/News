package com.news.backendproject.verify;

import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotCaptchaVerify {
    private final StringRedisTemplate stringRedisTemplate;
    public ApiResponse<GeneralDto> verify(String captcha, String redisKey) {
        String redisValue = null;
        redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        // 验证码不存在（过期或未生成）
        if (redisValue == null) {
            return new ApiResponse<>(400, "请重新获取验证码", new GeneralDto(false, null));
        }
        // 验证码匹配校验
        if(redisValue.trim().equals(captcha.trim())){
            // 验证成功后删除Key，防止重复使用
            stringRedisTemplate.delete(redisKey);
            return new ApiResponse<>(200,"验证码正确",new GeneralDto(true,null));
        } else {
            return new ApiResponse<>(400,"验证码错误",new GeneralDto(false,null));
        }
    }
}
