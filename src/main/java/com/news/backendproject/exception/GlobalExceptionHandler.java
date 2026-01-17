package com.news.backendproject.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDto;
import com.news.backendproject.utils.GetClientIp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import jakarta.mail.MessagingException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器：统一捕获并处理所有Controller层异常
 */
@Slf4j // 用于日志记录
@RestControllerAdvice // 作用于所有@RestController
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final GetClientIp getClientIp;

    // 处理日期解析异常
    @ExceptionHandler(DateTimeParseException.class)
    public ApiResponse<GeneralDto> handleDateTimeParseException(DateTimeParseException e, HttpServletRequest request) {
        log.error("日期解析/校验异常（IP：{}）：", getClientIp.get(request), e);
        // 直接返回异常的自定义消息
        return new ApiResponse<>(400, e.getMessage(), new GeneralDto(false, null));
    }

    // 处理试图访问不存在的接口
    @ExceptionHandler(NoResourceFoundException.class)
    public ApiResponse<GeneralDto> handleNoResourceFoundException(NoResourceFoundException e,HttpServletRequest request){
        log.error("试图访问不存在的接口,ip:"+getClientIp.get(request));
        return new ApiResponse<>(400, "非法请求", new GeneralDto(false, null));
    }
    // 处理JSON解析异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<GeneralDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("JSON解析异常：", e);
        String errorMsg = "非法请求参数";
        Throwable rootCause = e.getRootCause();
        // 判断是否是日期解析错误
        if (rootCause instanceof DateTimeParseException) {
            errorMsg = "日期格式错误";
        }
        return new ApiResponse<>(400, errorMsg, new GeneralDto(false, null));
    }


    /**
     * 处理参数缺失异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse<GeneralDto> handleMissingParamException(MissingServletRequestParameterException e) {
        log.error("参数缺失异常：", e);
        return new ApiResponse<>(400, "缺少必须参数", new GeneralDto(false, null));
    }

    /**
     * 处理参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResponse<GeneralDto> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("参数类型不匹配异常：", e);
        String errorMsg = "参数类型错误：" + e.getName() + "需为" + e.getRequiredType().getSimpleName();
        return new ApiResponse<>(400, errorMsg, new GeneralDto(false, null));
    }
    /**
     *处理参数格式异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<GeneralDto> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("用户传入了异常参数", e);
        String errorMsg = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage) // 拿到@Pattern里的message
                .collect(Collectors.joining("；")); // 多个错误用分号分隔
        // 若没有解析到自定义信息，返回默认提示
        if (errorMsg.isEmpty()) {
            errorMsg = "参数格式错误";
        }
        return new ApiResponse<>(400,errorMsg, new GeneralDto(false, null));
    }
    /**
     * 处理@RequestBody + @Valid 实体类验证失败的异常（MethodArgumentNotValidException）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<GeneralDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("用户传入了异常参数", e);
        return new ApiResponse<>(400, "异常参数", new GeneralDto(false, null));
    }

    /**
     *
     * 处理operationType等参数被非法传入的情况
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<GeneralDto> handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) {
        log.error("客户端非法操作：", e);
        return new ApiResponse<>(400, "客户端非法操作", new GeneralDto(false, null));
    }

    // ======================第三方服务异常 ======================
    /**
     * 处理Redis连接/操作异常
     */
    @ExceptionHandler({RedisConnectionFailureException.class, DataAccessException.class})
    public ApiResponse<GeneralDto> handleRedisException(Exception e) {
        log.error("Redis操作异常：", e);
        return new ApiResponse<>(500, "Redis升级中，请稍后重试", new GeneralDto(false, null));
    }

    /**
     *
     * 捕捉数据库异常
     */

    @ExceptionHandler(SQLException.class)
    public ApiResponse<GeneralDto> handleSQLException(SQLException e) {
        log.error("数据库服务异常", e);
        return new ApiResponse<>(500, "数据库升级中，请稍后重试", new GeneralDto(false, null));
    }
    /**
     * 处理邮件发送异常
     */
    @ExceptionHandler(MessagingException.class)
    public ApiResponse<GeneralDto> handleMessagingException(MessagingException e) {
        log.error("邮件发送异常：", e);
        return new ApiResponse<>(500, "邮箱验证码发送失败，请稍后重试", new GeneralDto(false, null));
    }

    // ====================== 系统通用异常 ======================
    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ApiResponse<GeneralDto> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常：", e);
        return new ApiResponse<>(500, "空指针异常", new GeneralDto(false, null));
    }

    /**
     * 处理IO异常（如文件读写、网络请求）
     */
    @ExceptionHandler(IOException.class)
    public ApiResponse<GeneralDto> handleIOException(IOException e) {
        log.error("IO异常：", e);
        return new ApiResponse<>(500, "IO操作异常，请稍后重试", new GeneralDto(false, null));
    }



    // ====================== 兜底异常（所有未捕获的异常） ======================
    @ExceptionHandler(Exception.class)
    public ApiResponse<GeneralDto> handleGlobalException(Exception e) {
        log.error("未知系统异常：", e); // 兜底异常打error日志，便于排查
        return new ApiResponse<>(500, "服务器升级中,稍后再试", new GeneralDto(false, null));
    }
}
