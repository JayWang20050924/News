package com.news.backendproject.annotation; // 替换为你的项目包名

import java.lang.annotation.*;

/**
 * 接口访问频率限制注解
 * 标注在接口方法上，定义「时间窗口内的最大访问次数」
 */
@Target({ElementType.METHOD}) // 仅作用于「方法」（接口方法）
@Retention(RetentionPolicy.RUNTIME) // 运行时生效（拦截器可通过反射读取）
@Documented // 生成 JavaDoc 时包含该注解
public @interface  AccessRestriction {
    /**
     * 核心属性：时间窗口内的最大访问次数（默认 10 次）
     * 示例：@AccessRestriction(limit = 5) → 时间窗口内最多访问 5 次
     */
    int limit() default 10;

    /**
     * 核心属性：时间窗口（单位：秒，默认 60 秒 = 1 分钟）
     * 示例：@AccessRestriction(period = 30) → 30 秒为一个时间窗口
     */
    int period() default 60;

    /**
     * 扩展属性：访问超限后的提示信息（默认值可自定义）
     * 示例：@AccessRestriction(message = "1分钟最多访问3次！") → 自定义提示
     */
    String message() default "请求频繁,请稍后再试";
    /**
     * 通过sessionid还是通过ip来限制用户,默认使用ip大范围拦截
     */
    boolean limitKey() default true;
    /**
     * （ 扩展属性：是否启用限制（默认启用，支持临时关闭）
     * 示例：@AccessRestriction(enabled = false) → 临时关闭该接口的频率限制
     */
    boolean enabled() default true;

}