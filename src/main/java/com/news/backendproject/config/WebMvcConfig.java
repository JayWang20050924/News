package com.news.backendproject.config;

import com.news.backendproject.interceptor.JwtInterceptor;
import com.news.backendproject.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private JwtInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，并配置拦截规则
        registry.addInterceptor(jwtInterceptor)
                // 指定需要拦截的接口
                .addPathPatterns("/getUserProfile")
                // 白名单不需要拦截的接口
                .excludePathPatterns("/static/**", "/error");
    }
}
