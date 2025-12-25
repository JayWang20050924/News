package com.news.backendproject.controller.user;

import com.google.code.kaptcha.Producer;
import com.news.backendproject.annotation.AccessRestriction;
import com.news.backendproject.dto.*;
import com.news.backendproject.entity.User;
import com.news.backendproject.service.*;
import com.news.backendproject.verify.BotCaptchaVerify;
import com.news.backendproject.verify.OperationTypeVerify;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * UserController:控制所有用户关联的行为
 */
@RestController
@RequiredArgsConstructor
@Validated // 启用参数验证
public class UserController {
    private final Producer kaptchaProducer;
    private final UserLoginService userLoginService;
    private final UserGetLoginStatusService userGetLoginStatusService;
    private final UserRegisterService userRegisterService;
    private final UserForgotService userForgotService;
    private final StringRedisTemplate stringRedisTemplate;
    private final BotCaptchaVerify botCaptchaVerify;
    private final SendEmailCaptchaService sendEmailCaptchaService;

    // 正则常量（抽离便于维护）
    private static final String REGEX_USER_PASS = "^[A-Za-z0-9]{8,20}$"; // 用户名/密码规则
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"; // 邮箱规则
    private static final String REGEX_BOT_CAPTCHA = "^[0-9a-z]{4}$"; // 人机验证码规则

    // 发送人机验证码
    @AccessRestriction(message = "验证码请求过于频繁,1分钟后再试")
    @GetMapping("/sendBotCaptcha")
    public void generateCaptcha(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam  @NotBlank String operationType) throws IOException {
        //使用枚举类限制接口请求
        if (    !(operationType.equals(OperationTypeVerify.LOGIN.getCode()))&&
                !(operationType.equals(OperationTypeVerify.FORGOT_PASSWORD.getCode())) &&
                !(operationType.equals(OperationTypeVerify.REGISTER.getCode()))
        ) {
          throw new IllegalArgumentException("非法参数");
        }

        // 禁用缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        // 设置响应类型为图片
        response.setContentType("image/jpeg");

        // 生成验证码原文
        String captchaOraginal = kaptchaProducer.createText();
        // 验证码Key：业务场景+sessionId
        String redisKey = operationType + request.getSession().getId();
        // 存入Redis，60秒过期
        stringRedisTemplate.opsForValue().set(
                redisKey,
                captchaOraginal,
                60,
                TimeUnit.SECONDS
        );
        // 生成验证码图片
        BufferedImage image = kaptchaProducer.createImage(captchaOraginal);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
        out.close();
    }

    // 人机验证码的验证接口
    @GetMapping("/botCheck")
    public ApiResponse<GeneralDataResponse> botCheck(
            // 人机验证码格式验证
            @RequestParam @Pattern(regexp = REGEX_BOT_CAPTCHA, message = "人机验证码格式错误") String captcha,
            @RequestParam @NotBlank String operationType,
            HttpServletRequest request) {
        //使用枚举类限制接口请求
        if (!(operationType.equals(OperationTypeVerify.LOGIN.getCode())) &&
                !(operationType.equals(OperationTypeVerify.FORGOT_PASSWORD.getCode())) &&
                !(operationType.equals(OperationTypeVerify.REGISTER.getCode()))
        ) {
            return new ApiResponse<>(400, "非法请求", new GeneralDataResponse(false, null));
        }
        String redisKey = operationType + request.getSession().getId();
        return botCaptchaVerify.verify(captcha, redisKey);
    }

    @AccessRestriction(limit = 30, message = "获取登录状态过于频繁")
    @GetMapping("/getLoginStatus")
    public ApiResponse<GeneralDataResponse> getLoginStatus(HttpServletRequest request) {
        return userGetLoginStatusService.getLoginStatus(request);
    }

    @AccessRestriction(limit = 5, message = "登录过于频繁,1分钟后再试", limitKey = false)
    @PostMapping("/getLoginResponse")
    public ApiResponse<GeneralDataResponse> getLoginResponse(
            // 用户名格式验证
            @RequestParam @Pattern(regexp = REGEX_USER_PASS, message = "用户名错误") String username,
            // 密码格式验证
            @RequestParam @Pattern(regexp = REGEX_USER_PASS, message = "密码错误") String password,
            // 人机验证码格式验证
            @RequestParam @Pattern(regexp = REGEX_BOT_CAPTCHA, message = "人机验证码错误") String captcha,
            @RequestParam @NotBlank String operationType,
            HttpServletRequest request) {
        // 限制验证请求为登录业务
        if (!operationType.equals(OperationTypeVerify.LOGIN.getCode())) {
            return new ApiResponse<>(400, "非法验证请求", new GeneralDataResponse(false, null));
        }
        String redisKey = operationType + request.getSession().getId();
        return userLoginService.userLogin(username, password, captcha, redisKey);
    }

    // 发送邮箱验证码
    @AccessRestriction(limit = 1, message = "验证码请求过于频繁,稍后再试", limitKey = false)
    @PostMapping("/sendEmailCaptcha")
    public ApiResponse<GeneralDataResponse> sendEmailCaptcha(
            @RequestBody @Valid SendEmailCaptchaDTO dto,
            HttpServletRequest request) {
        String redisKey = dto.getOperationType() + request.getSession().getId();
        //通过枚举类获得当前操作类型
        OperationTypeVerify operationType = OperationTypeVerify.getByCode(dto.getOperationType());
        System.out.println(dto.toString());
        switch (operationType) {
            case REGISTER -> {
                return sendEmailCaptchaService.sendRegister(dto, redisKey);
            }
            case FORGOT_PASSWORD -> {
                return sendEmailCaptchaService.sendForgotPwd(dto, redisKey);
            }
            default -> {
                return new ApiResponse<>(400, "非法请求", new GeneralDataResponse(false, null));
            }
        }

    }

    //注册接口包含验证邮箱验证码
    @AccessRestriction(limit = 5, message = "注册过于频繁,1分钟后再试", limitKey = false)
    @PostMapping("/getRegisterResponse")
    public ApiResponse<GeneralDataResponse> getRegisterResponse(
            // @Valid 触发User实体类的字段验证
            @RequestBody @Valid UserRegisterDto dto,
            HttpServletRequest request) {
        if (!dto.getOperationType().equals(OperationTypeVerify.REGISTER.getCode())) {
            return new ApiResponse<>(400, "非法请求", new GeneralDataResponse(false, null));
        }
        String redisKey = dto.getOperationType() + request.getSession().getId();
        System.out.println(dto);
        return userRegisterService.userRegister(dto, redisKey);
    }

    // 找回密码接口
    @AccessRestriction(limit = 5, message = "找回密码过于频繁,1分钟后再试", limitKey = false)
    @PostMapping("/getForgotResponse")
    public ApiResponse<GeneralDataResponse> userForgotPassword(
            @RequestBody @Valid UserForgotDto dto,
            HttpServletRequest request
    ){
        if (!dto.getOperationType().equals(OperationTypeVerify.FORGOT_PASSWORD.getCode())) {
            return new ApiResponse<>(400, "非法请求", new GeneralDataResponse(false, null));
        }
        System.out.println(dto);
        String redisKey = dto.getOperationType() + request.getSession().getId();
        return userForgotService.forgot(dto,redisKey);
    }

    @AccessRestriction(limit = 10, message = "获取个人信息过于频繁,1分钟后再试", limitKey = false)
    @GetMapping("/getSelfProfile")
    public ApiResponse<GeneralDataResponse> getSelfProfile() {
        GeneralDataResponse data = new GeneralDataResponse(false, null);
        return new ApiResponse<>(200, "获取信息", data);
    }
}