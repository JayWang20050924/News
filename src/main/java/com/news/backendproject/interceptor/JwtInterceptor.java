package com.news.backendproject.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.backendproject.annotation.JwtRequired;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import com.news.backendproject.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    @Value("${jwt.blacklist-prefix}")
    private String blacklistPrefix;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate stringRedisTemplate;
    //todo: 添加黑名单校验
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //  判断当前处理器是否是控制器方法（HandlerMethod），静态资源等非方法类型直接放行
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        //  检查该方法是否有@JwtRequired注解，无注解则直接放行
        JwtRequired jwtRequired = handlerMethod.getMethodAnnotation(JwtRequired.class);
        if (jwtRequired == null) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("令牌为空，请求路径：" + request.getRequestURI() + " time:" + LocalDateTime.now());
            // 返回未登录的JSON响应（401未授权）
            buildErrorResponse(response, 401, "您还未登录哦");
            return false;
        }

        // 提取令牌（去掉Bearer前缀）
        String token = authHeader.substring(7);
        //  是否被拉黑,防止jwt被盗取
        String redisKey=blacklistPrefix+token;
        Boolean blacklistResult = stringRedisTemplate.hasKey(redisKey);
        if (blacklistResult){
            log.warn("疑似存在盗用jwt行为");
            buildErrorResponse(response, 401, "当前用户已退出,请重新登录");
            return false;
        }
        try {
            // 校验令牌并提取用户名
            String username = jwtUtil.extractUsername(token);
            request.setAttribute("currentUsername", username);
            System.out.println("令牌通过校验，用户名：" + username + " 请求路径：" + request.getRequestURI() + " time:" + LocalDateTime.now());
            return true; // 校验通过，放行
        } catch (JwtException e) {
            // 令牌无效（过期、签名错误等），返回统一的JSON错误响应
            System.out.println("令牌无效：" + e.getMessage() + " 请求路径：" + request.getRequestURI() + " time:" + LocalDateTime.now());
            buildErrorResponse(response, 401, "令牌无效或已过期，请重新登录");
            return false; // 拦截
        }
    }

    /**
     * 封装统一的错误响应（复用逻辑，避免代码冗余）
     * @param response 响应对象
     * @param code 业务错误码
     * @param message 错误提示信息
     */
    private void buildErrorResponse(HttpServletResponse response, int code, String message) throws Exception {
        // 设置响应头（JSON格式+UTF-8编码）
        response.setContentType("application/json;charset=UTF-8");
        //  自定义错误类型响应头
        response.setHeader("X-Error-Type", "jwt-invalid");
        //  构建统一的返回体
        ApiResponse<GeneralDataResponse> apiResponse = new ApiResponse<>(
                code,
                message,
                new GeneralDataResponse(false, null)
        );
        //  写入响应体（指定UTF-8，避免中文乱码）
        try (OutputStream outputStream = response.getOutputStream()) {
            objectMapper.writeValue(outputStream, apiResponse);
            outputStream.flush(); // 强制刷新，确保数据立即写入
        }
    }
}