package com.news.backendproject.controller.user;

import com.google.code.kaptcha.Producer;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.LoginStatusResponse;
import com.news.backendproject.entity.RegisteStatusResponse;
import com.news.backendproject.service.UserGetLoginStatusService;
import com.news.backendproject.service.UserLoginService;
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
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 禁用缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        // 设置响应类型为图片
        response.setContentType("image/jpeg");

        // 生成验证码文本
        String captchaOraginal = kaptchaProducer.createText();
        // 验证码文本存入Session（用于后续验证）
        HttpSession session = request.getSession();
        // 设置uuid作为验证码唯一标识
        String captchaId = UUID.randomUUID().toString().replace("-", "");
        // captchaId为key,验证码原文为value
        session.setAttribute(captchaId, captchaOraginal);
        session.setMaxInactiveInterval(60); // 单位：秒
        //将UUID通过响应头传递给前端（自定义头，X-Captcha-Id）
        response.setHeader("X-Captcha-Id", captchaId);
        // 生成验证码图片
        BufferedImage image = kaptchaProducer.createImage(captchaOraginal);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
        out.close();
    }


    @PostMapping("/getLoginResponse")
    public ApiResponse<LoginStatusResponse> getLoginResponse(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String captcha,
            @RequestParam String captchaId,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
       return userLoginService.userLogin(username,password,captcha,captchaId,request,response);
    }

    @GetMapping("/getLoginStatus")
    //<LoginStatusResponse>指定data类型
    public ApiResponse<LoginStatusResponse> getLoginStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return userGetLoginStatusService.getLoginStatus(request);
    }

    @PostMapping("/getRegisterResponse")
    public ApiResponse<RegisteStatusResponse> getRegisterResponse(){
        boolean register=true;
        RegisteStatusResponse data = new RegisteStatusResponse(register,null);
        return new ApiResponse<>(200,"注册成功",data);
    }
    @GetMapping("/getUserProfile")
    public ApiResponse<RegisteStatusResponse> getUserProfile(){
        boolean registResult=false;
        RegisteStatusResponse data = new RegisteStatusResponse(registResult,null);
        return new ApiResponse<>(200,"获取信息",data);
    }
}
