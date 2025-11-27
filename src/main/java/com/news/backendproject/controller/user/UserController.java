package com.news.backendproject.controller.user;

import com.google.code.kaptcha.Producer;
import com.news.backendproject.annotation.AccessRestriction;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GenaralDataResponse;
import com.news.backendproject.service.UserGetLoginStatusService;
import com.news.backendproject.service.UserLoginService;
import com.news.backendproject.service.UserRegisterService;
import com.news.backendproject.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * UserController:控制所有用户关联的行为
 */
@RestController
public class UserController {

    @Autowired
    private Producer kaptchaProducer;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserGetLoginStatusService userGetLoginStatusService;
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @AccessRestriction(message = "验证码请求过于频繁,1分钟后再试")
    @GetMapping("/captcha")
    public void generateCaptcha(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam String operationType) throws IOException {
        // 禁用缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        // 设置响应类型为图片
        response.setContentType("image/jpeg");

        // 生成验证码原文
        // 验证码原文为value
        String captchaOraginal = kaptchaProducer.createText();

        HttpSession session = request.getSession();
        // 获取sessionId
        String sessionId = session.getId();
        // 设置业务场景operationType+sessionId作为验证码唯一标识key
        String captchaKey =operationType+"-"+sessionId;
        // Secure=true的作用:浏览器仅会在「HTTPS 协议的请求」中，携带标记为 Secure=true 的 Cookie
        // 过期时间1分钟,httpOnly=true（禁止前端读取）
        CookieUtil.setCookie(response, captchaKey, captchaOraginal, 60, false, true);
        // 生成验证码图片
        BufferedImage image = kaptchaProducer.createImage(captchaOraginal);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
        out.close();
    }
    @AccessRestriction(limit = 5,period = 60,message = "登录过于频繁,1分钟后再试")
    @PostMapping("/getLoginResponse")
    public ApiResponse<GenaralDataResponse> getLoginResponse(
            //@RequestParam定义的参数必须传入，否则400错误
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String captcha,
            @RequestParam String operationType,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        //限制访问携带的验证码来自于登录业务
        if (!operationType.equals("login")) {
            //抛出400错误,前端使用try-catch配合element-plus处理
            return new ApiResponse<>(400,"非法验证请求",new GenaralDataResponse(false,null));
        }
        String captchaKey=operationType+"-"+request.getSession().getId();
        return userLoginService.userLogin(username,password,captcha,captchaKey,request,response);
    }

    @GetMapping("/getLoginStatus")
    //指定data类型
    public ApiResponse<GenaralDataResponse> getLoginStatus(
            HttpServletRequest request,
            HttpServletResponse response
            ) throws IOException {
        return userGetLoginStatusService.getLoginStatus(request);
    }
    //单独验证人机接口，对应前端单独人机验证组件
    @GetMapping("/botCheck")
    public ApiResponse<GenaralDataResponse>  botCheck(
            @RequestParam String captcha,
            @RequestParam String operationType,
            HttpServletRequest request,
            HttpServletResponse response){
        //先验证业务场景是否合法
        if (!operationType.equals("register")) {

            //抛出400错误,前端使用try-catch配合element-plus处理
            return new ApiResponse<>(400,"非法验证请求operationType:"+operationType,new GenaralDataResponse(false,null));
        }
        //获取当前请求来源的行为类型+sessionid组成key值查询对应value
        String captchaKey=operationType+"-"+request.getSession().getId();
        // 从请求的httpOnly的Cookie 中获取存储的验证码值
        String captchaValue = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.err.println("cookiename:"+cookie.getName());
                //有存储了的验证码键值对应上了当前业务获取验证码原文
                if (cookie.getName().equals(captchaKey)) {
                    captchaValue = cookie.getValue();
                    System.err.println("cookievalue:"+captchaValue);
                    break;
                }
            }
        }
        //验证码过期(只有验证码键对应后captchaValue才会被赋值)
        if (captchaValue == null) {
            GenaralDataResponse data = new GenaralDataResponse(false, null);
            //抛出400错误让前端使用try-catch配合element-plus处理
            return new ApiResponse<>(400, "验证码已过期", data);
        }
        System.out.println("captchaValue:"+captchaValue);
        //验证码不匹配
        if(captchaValue.trim().equals(captcha.trim())){
            //返回验证码正确的结果
            GenaralDataResponse data = new GenaralDataResponse(true,null);
            return new ApiResponse<>(200,"验证码正确",data);
        }else if (!captchaValue.trim().equals(captcha.trim())){
            //返回验证码错误的结果
            GenaralDataResponse data = new GenaralDataResponse(false,null);
            return new ApiResponse<>(400,"验证码错误",data);
        }
        GenaralDataResponse data = new GenaralDataResponse(false,null);
        return new ApiResponse<>(400,"未知错误请重试",data);
    }


    @PostMapping("/getRegisterResponse")
    public ApiResponse<GenaralDataResponse> getRegisterResponse(){
        return userRegisterService.userRegister(null);
    }


    @GetMapping("/getUserProfile")
    public ApiResponse<GenaralDataResponse> getUserProfile(){
        GenaralDataResponse data = new GenaralDataResponse(false,null);
        return new ApiResponse<>(200,"获取信息",data);
    }
}
