package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.domain.User;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.LoginStatusResponse;
import com.news.backendproject.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public  class UserLoginService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDaoImp userDaoImp;
    public ApiResponse<LoginStatusResponse> userLogin(
            String username,
            String password,
            String captcha,
            String captchaKey,
            HttpServletRequest request,
            HttpServletResponse response

    ){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        // 从请求的httpOnly的Cookie 中获取存储的验证码值
        String cptchaValue = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.err.println("cookiename:"+cookie.getName());
                //有存储了的验证码键值对应上了当前业务获取验证码原文
                if (cookie.getName().equals(captchaKey)) {
                    cptchaValue = cookie.getValue();
                    System.err.println("cookievalue:"+cptchaValue);
                    break;
                }
            }
        }
        if (cptchaValue == null) {
            LoginStatusResponse data = new LoginStatusResponse(false, null);
            //抛出400错误让前端使用try-catch配合element-plus处理
            return new ApiResponse<>(400, "验证码已过期", data);
        }
        System.out.println("cptchaValue:"+cptchaValue);
        //验证码不匹配或过期
        if(!cptchaValue.trim().equals(captcha.trim())){
            //返回验证码错误的结果
            LoginStatusResponse data = new LoginStatusResponse(false,null);
            return new ApiResponse<>(400,"验证码错误",data);
        }
        //用户不存在
        else if(cptchaValue.trim().equals(captcha.trim())
                &&userDaoImp.verifyUserExistenceService(user)==0){
            LoginStatusResponse data = new LoginStatusResponse(false,null);
            return new ApiResponse<>(404,"用户不存在",data);
        }
        //密码错误
        else if (cptchaValue.trim().equals(captcha.trim())
                &&userDaoImp.verifyUserExistenceService(user)!=0
                &&userDaoImp.verifyUserPasswordService(user)==0) {
            LoginStatusResponse data = new LoginStatusResponse(false,null);
            return new ApiResponse<>(404,"密码错误",data);
        }
        //登录成功
        else if (cptchaValue.trim().equals(captcha.trim())
                &&userDaoImp.verifyUserExistenceService(user)!=0
                &&userDaoImp.verifyUserPasswordService(user)!=0){
            String token = jwtUtil.generateToken(user.getUsername()); // 传入用户名生成令牌
            LoginStatusResponse data = new LoginStatusResponse(true,token);
            System.err.println("接口发送的token:"+token);
            return new ApiResponse<>(200,"登录成功",data);
        }
        LoginStatusResponse data = new LoginStatusResponse(false,null);
        return new ApiResponse<>(404,"未知的错误",data);
    }
}
