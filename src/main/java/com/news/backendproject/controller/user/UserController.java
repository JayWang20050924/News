package com.news.backendproject.controller.user;

import com.google.code.kaptcha.Producer;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.LoginStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * UserController:控制所有用户关联的行为
 */
@RestController
public class UserController {

    @Autowired
    private Producer kaptchaProducer;

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
        String captchaText = kaptchaProducer.createText();
        // 存入Session（用于后续验证）
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaText);

        // 生成验证码图片
        BufferedImage image = kaptchaProducer.createImage(captchaText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
        out.close();
    }

    @GetMapping("/requestLogin")
    public ApiResponse<LoginStatusResponse> requestLogin() {
        return null;
    }



    @GetMapping("/getLoginStatus")
    //<LoginStatusResponse>指定data类型
    public ApiResponse<LoginStatusResponse> getLoginStatus(){
        boolean isLogin=false;
        LoginStatusResponse data = new LoginStatusResponse(isLogin);
        return new ApiResponse<>(200,"未登录",data);
    }
}
