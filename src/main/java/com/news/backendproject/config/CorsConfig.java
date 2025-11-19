package com.news.backendproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 标记为配置类，Spring启动时自动加载
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // 对所有接口生效（可指定路径，如 "/api/**" 仅对/api下接口生效）
                .addMapping("/**")
                // 允许的前端域名（开发环境可写"*"，生产环境必须指定具体域名）
                .allowedOrigins("http://localhost:8080", "http://localhost:5173/")
                // 允许的HTTP方法（GET/POST/PUT/DELETE等）
                .allowedMethods("*") // "*" 表示允许所有方法
                // 允许的请求头（如Content-Type、Authorization等）
                .allowedHeaders("*")
                // 暴露自定义响应头（前端需要获取的头）
                .exposedHeaders("X-Captcha-Id")
                // 允许携带凭证（Cookie、Token等，前后端需一致开启）
                .allowCredentials(true)
                // 预检请求的缓存时间（3600秒=1小时，减少重复预检请求）
                .maxAge(3600);
    }
}