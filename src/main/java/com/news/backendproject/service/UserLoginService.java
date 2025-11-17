package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.domain.User;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.LoginStatusResponse;
import com.news.backendproject.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public  class UserLoginService {
    @Autowired
    private JwtUtil jwtUtil;
    public ApiResponse<LoginStatusResponse> userLogin(
            String username,
            String password,
            String captcha,
            HttpServletRequest request,
            HttpServletResponse response

    ){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        //前端返回的参数值测试
        System.out.println(username+password+captcha);
        HttpSession session = request.getSession();
        //session获取验证码的创建时间
        Long captchaCreateTime = (Long) session.getAttribute("captchaCreateTime");

        System.out.println("captchaCreateTime:"+captchaCreateTime);

        //session获取验证码原文
        String captchaOriginal=(String) session.getAttribute("captcha");

        System.out.println("captchaOriginal:"+captchaOriginal);
        //判断登录是否成功
        boolean loginResult = false;
        //验证码不匹配或过期
        if(!captchaOriginal.trim().equals(captcha.trim())
                 ||System.currentTimeMillis()-captchaCreateTime>60000){
            //返回验证码错误的结果
            LoginStatusResponse data = new LoginStatusResponse(loginResult,null);
            return new ApiResponse<>(400,"验证码错误或过期",data);
        }
        //用户不存在
        else if(captchaOriginal.trim().equals(captcha.trim())
                &&new UserDaoImp().verifyUserExistenceService(user)==0){
            LoginStatusResponse data = new LoginStatusResponse(loginResult,null);
            return new ApiResponse<>(404,"用户不存在",data);
        }
        //密码错误
        else if (captchaOriginal.trim().equals(captcha.trim())
                &&new UserDaoImp().verifyUserExistenceService(user)!=0
                &&new UserDaoImp().verifyUserPasswordService(user)==0) {
            LoginStatusResponse data = new LoginStatusResponse(loginResult,null);
            return new ApiResponse<>(404,"密码错误",data);
        }
        //登录成功
        else if (captchaOriginal.trim().equals(captcha.trim())
                &&new UserDaoImp().verifyUserExistenceService(user)!=0
                &&new UserDaoImp().verifyUserPasswordService(user)!=0){
            loginResult=true;
            String token = jwtUtil.generateToken(user.getUsername()); // 传入用户名生成令牌
            LoginStatusResponse data = new LoginStatusResponse(loginResult,token);
            System.err.println("token:"+token);
            return new ApiResponse<>(200,"登录成功",data);
        }
        LoginStatusResponse data = new LoginStatusResponse(loginResult,null);
        return new ApiResponse<>(404,"未知的错误",data);
    }
}
