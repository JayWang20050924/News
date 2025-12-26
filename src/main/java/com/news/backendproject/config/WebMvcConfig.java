package com.news.backendproject.config;

import com.news.backendproject.interceptor.AccessRestrictionInterceptor;
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
    @Resource
    private AccessRestrictionInterceptor accessRestrictionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //  限流拦截器：order=1（先执行）,自定义注解控制
        registry.addInterceptor(accessRestrictionInterceptor)
                .addPathPatterns("/**") // 拦截所有接口
                .excludePathPatterns("/static/**", "/error")
                .order(1); // 优先级1，先执行

        //  Jwt拦截器：order=2（后执行）,自定义注解控制
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 拦截所有接口
                .excludePathPatterns("/static/**", "/error")
                .order(2); // 优先级2，后执行
    }
}