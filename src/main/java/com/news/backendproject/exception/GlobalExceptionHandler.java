package com.news.backendproject.exception;

import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GenaralDataResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器：统一捕获并处理所有Controller层异常
 */
@Slf4j // 用于日志记录
@RestControllerAdvice // 作用于所有@RestController
public class GlobalExceptionHandler {

    /**
     * 处理参数缺失异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse<GenaralDataResponse> handleMissingParamException(MissingServletRequestParameterException e) {
        log.error("参数缺失异常：", e);
        return new ApiResponse<>(400, "缺少必须参数", new GenaralDataResponse(false, null));
    }

    /**
     * 处理参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResponse<GenaralDataResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("参数类型不匹配异常：", e);
        String errorMsg = "参数类型错误：" + e.getName() + "需为" + e.getRequiredType().getSimpleName();
        return new ApiResponse<>(400, errorMsg, new GenaralDataResponse(false, null));
    }

    // ======================第三方服务异常 ======================
    /**
     * 处理Redis连接/操作异常
     */
    @ExceptionHandler({RedisConnectionFailureException.class, DataAccessException.class})
    public ApiResponse<GenaralDataResponse> handleRedisException(Exception e) {
        log.error("Redis操作异常：", e);
        return new ApiResponse<>(500, "缓存服务异常，请稍后重试", new GenaralDataResponse(false, null));
    }
    /**
     * 处理邮件发送异常
     */
    @ExceptionHandler(MessagingException.class)
    public ApiResponse<GenaralDataResponse> handleMessagingException(MessagingException e) {
        log.error("邮件发送异常：", e);
        return new ApiResponse<>(500, "邮箱验证码发送失败，请稍后重试", new GenaralDataResponse(false, null));
    }

    // ====================== 系统通用异常 ======================
    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ApiResponse<GenaralDataResponse> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常：", e);
        return new ApiResponse<>(500, "空指针异常", new GenaralDataResponse(false, null));
    }

    /**
     * 处理IO异常（如文件读写、网络请求）
     */
    @ExceptionHandler(IOException.class)
    public ApiResponse<GenaralDataResponse> handleIOException(IOException e) {
        log.error("IO异常：", e);
        return new ApiResponse<>(500, "IO操作异常，请稍后重试", new GenaralDataResponse(false, null));
    }

    /**
     *处理参数格式异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<GenaralDataResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("参数格式异常", e);
        return new ApiResponse<>(500, "参数格式异常", new GenaralDataResponse(false, null));
    }
    // ====================== 兜底异常（所有未捕获的异常） ======================
    @ExceptionHandler(Exception.class)
    public ApiResponse<GenaralDataResponse> handleGlobalException(Exception e) {
        log.error("未知系统异常：", e); // 兜底异常打error日志，便于排查
        return new ApiResponse<>(500, "服务器升级中,稍后再试", new GenaralDataResponse(false, null));
    }
}
