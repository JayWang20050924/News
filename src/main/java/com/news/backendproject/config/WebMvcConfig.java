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
        //  限流拦截器：order=1（先执行）
        registry.addInterceptor(accessRestrictionInterceptor)
                .addPathPatterns("/**") // 拦截所有接口
                .excludePathPatterns(
                        "/static/**",       // 排除静态资源
                        "/error",           // 排除错误页
                        "/actuator/**"      // 若集成了健康检查，排除（可选）
                )
                .order(1); // 优先级1，先执行

        //  Jwt拦截器：order=2（后执行）
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns(
                        "/getUserProfile",  // 需要登录的接口
                        "/api/user/**"      // 若有其他登录态接口，统一添加
                )
                .excludePathPatterns("/static/**", "/error")
                .order(2); // 优先级2，后执行
    }
}