package com.news.backendproject.service;


import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDto;
import com.news.backendproject.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlacklistJwtService {
    @Value("${jwt.blacklist-prefix}")
    private String blacklistPrefix;
    private final StringRedisTemplate stringRedisTemplate;
    private final JwtUtil jwtUtil;
    public ApiResponse<GeneralDto> addBlacklist(String token, String reason) {
        String username = jwtUtil.extractUsername(token);
        if (!jwtUtil.validateToken(token, username)){
            return new ApiResponse<>(401,"认证失效 ",new GeneralDto(false,null));
        }
        String redisKey = blacklistPrefix + token;
        //若缺则设
        Boolean redisSetResult = stringRedisTemplate.opsForValue().setIfAbsent(redisKey, reason);
        if (Boolean.FALSE.equals(redisSetResult)){
            return new ApiResponse<>(401,"当前用户已退出,请重新登录",new GeneralDto(false,null));
        }
        return new ApiResponse<>(200,"退出成功",new GeneralDto(true,null));
    }
}
