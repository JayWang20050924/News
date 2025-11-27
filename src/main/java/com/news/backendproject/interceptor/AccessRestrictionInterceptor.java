package com.news.backendproject.interceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.backendproject.annotation.AccessRestriction;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GenaralDataResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * 访问频率限制拦截器：处理 @AccessRestriction 注解的核心逻辑
 */
@Component
public class AccessRestrictionInterceptor implements HandlerInterceptor {

    // Redis 模板：用于存储访问计数（分布式环境推荐）
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ObjectMapper objectMapper;
    /**
     * 接口调用前执行：判断是否超限
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 仅处理标注 @AccessRestriction 的方法（过滤非接口方法）
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true; // 不是目标方法，直接放行
        }

        // 获取方法上的 @AccessRestriction 注解
        AccessRestriction annotation = handlerMethod.getMethodAnnotation(AccessRestriction.class);
        if (annotation == null || !annotation.enabled()) {
            return true; // 未标注注解 或 已关闭限制，直接放行
        }

        // 解析注解参数
        int limit = annotation.limit(); // 最大访问次数
        int period = annotation.period(); // 时间窗口（秒）
        String message = annotation.message(); // 超限提示

        // 生成唯一key：区分不同接口 + 不同访问者（这里用 IP 作为访问者标识）
        String ip = getClientIp(request); // 获取客户端IP
        String methodName = handlerMethod.getMethod().getName(); // 接口方法名
        String redisKey = "access_limit:" + methodName + ":" + ip;
        //未过期的 Key 会持续累计访问次数，只要在有效期内次数超限，就会拦截
        // Redis 计数：原子递增（避免并发问题）
        Long count=null;
        try{
        count = stringRedisTemplate.opsForValue().increment(redisKey, 1);
        if (count == 1) {
            // 第一次访问，设置key的过期时间（等于时间窗口）
            stringRedisTemplate.expire(redisKey, period, TimeUnit.SECONDS);
            return true;
        }}catch (Exception e){
            // Redis宕机/连接失败：降级放行
            System.err.println("Redis不可用:"+e.getMessage());
            return true; // 放行，避免网站不可用
        }

        // 判断是否超限
        if (count != null&&count > limit) {
            // 超限：返回提示信息
            GenaralDataResponse data = new GenaralDataResponse(false,null);
            ApiResponse<GenaralDataResponse> apiResponse = new ApiResponse<>(429, message, data);
            //自定义错误频率响应头
            response.setHeader("X-Error-Type", "rate-limit");
            // 设置响应头（JSON格式+Retry-After）
            response.setContentType("application/json;charset=UTF-8");
            //返回结果
            try (OutputStream outputStream = response.getOutputStream()) {
                objectMapper.writeValue(outputStream, apiResponse);
                outputStream.flush(); // 强制刷新，确保数据立即写入
            }
            return false; // 拦截，不执行接口逻辑
        }

        // 未超限：放行
        return true;
    }

    /**
     * 获取客户端真实IP（处理反向代理场景，如Nginx）
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多IP场景（X-Forwarded-For可能返回多个IP，取第一个）
        return ip != null ? ip.split(",")[0].trim() : "unknown";
    }
}