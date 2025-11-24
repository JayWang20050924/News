package com.news.backendproject.controller.user;

import com.google.code.kaptcha.Producer;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GenaralDataResponse;
import com.news.backendproject.service.UserGetLoginStatusService;
import com.news.backendproject.service.UserLoginService;
import com.news.backendproject.utils.CookieUtil;
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
    private StringRedisTemplate stringRedisTemplate;
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

    @PostMapping("/getLoginResponse")
    public ApiResponse<GenaralDataResponse> getLoginResponse(
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

    @GetMapping("/verifyCaptcha")
    public ApiResponse<GenaralDataResponse>  verifyCaptcha(
            @RequestParam String captcha,
            @RequestParam String operationType,
            HttpServletRequest request,
            HttpServletResponse response){
        GenaralDataResponse data = new GenaralDataResponse(true,null);
        return new ApiResponse<>(200,"验证成功",data);
    }
    @PostMapping("/getRegisterResponse")
    public ApiResponse<GenaralDataResponse> getRegisterResponse(){
        GenaralDataResponse data = new GenaralDataResponse(true,null);
        return new ApiResponse<>(200,"注册成功",data);
    }


    @GetMapping("/getUserProfile")
    public ApiResponse<GenaralDataResponse> getUserProfile(){
        GenaralDataResponse data = new GenaralDataResponse(false,null);
        return new ApiResponse<>(200,"获取信息",data);
    }
}
