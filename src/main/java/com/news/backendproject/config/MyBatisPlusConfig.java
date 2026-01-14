package com.news.backendproject.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus分页插件配置
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * 注册分页拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页拦截器
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 指定数据库类型
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        // 设置最大分页条数，防止分页参数过大导致性能问题（比如最多查1000条）
        paginationInnerInterceptor.setMaxLimit(1000L);
        // 将分页拦截器添加到MP拦截器链
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}