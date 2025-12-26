package com.news.backendproject.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD}) // 仅作用于方法
@Retention(RetentionPolicy.RUNTIME) // 运行时保留，拦截器可反射读取
@Documented // 生成 JavaDoc 时包含该注解
public @interface JwtRequired {
}
