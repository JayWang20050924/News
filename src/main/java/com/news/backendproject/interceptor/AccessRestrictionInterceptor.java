package com.news.backendproject.interceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.backendproject.annotation.AccessRestriction;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDto;
import com.news.backendproject.utils.GetClientIp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * 访问频率限制拦截器：处理 @AccessRestriction 注解的核心逻辑
 */
@Component
@RequiredArgsConstructor
public class AccessRestrictionInterceptor implements HandlerInterceptor {
    // Redis 模板：用于存储访问计数
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;
    private final GetClientIp getClientIp;
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
        String message = annotation.message();// 超限提示
        boolean limitKey= annotation.limitKey();
        String redisKey =null;
        // 生成唯一key：区分不同接口 + 不同访问者（用 IP或sessionid [true|false]作为访问者标识）
        if (limitKey){
            String  ip = getClientIp.get(request); // 获取客户端IP
            String methodName = handlerMethod.getMethod().getName(); // 接口方法名
            redisKey = "access_limit:" + methodName + ":" + ip;
        }else {
            String sessionId = request.getSession().getId();
            String methodName = handlerMethod.getMethod().getName();
            redisKey = "access_limit:" + methodName + ":" + sessionId;
        }
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
            buildErrorResponse(response,429,message);
            return false; // 拦截，不执行接口逻辑
        }

        // 未超限：放行
        return true;
    }
    private void buildErrorResponse(HttpServletResponse response, int code, String message) throws Exception {
        // 设置响应头（JSON格式+UTF-8编码）
        response.setContentType("application/json;charset=UTF-8");
        //  自定义错误类型响应头
        response.setHeader("X-Error-Type", "rate-limit");
        //  构建统一的返回体
        ApiResponse<GeneralDto> apiResponse = new ApiResponse<>(
                code,
                message,
                new GeneralDto(false, null)
        );
        //  写入响应体（指定UTF-8，避免中文乱码）
        try (OutputStream outputStream = response.getOutputStream()) {
            objectMapper.writeValue(outputStream, apiResponse);
            outputStream.flush(); // 强制刷新，确保数据立即写入
        }
    }
}