package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.domain.User;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GenaralDataResponse;
import com.news.backendproject.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service

public  class UserLoginService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDaoImp userDaoImp;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ApiResponse<GenaralDataResponse> userLogin(
            String username,
            String password,
            String captcha,
            String captchaKey

    ){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        // 从Redis读取验证码
        String captchaValue = null;
        try {
            captchaValue = stringRedisTemplate.opsForValue().get(captchaKey);
        } catch (Exception e) {
            return new ApiResponse<>(500, "服务器升级中稍后再试", new GenaralDataResponse(false, null));
        }

        // 验证码过期/不存在
        if (captchaValue == null) {
            return new ApiResponse<>(400, "验证码已过期", new GenaralDataResponse(false, null));
        }

        // 验证码不匹配
        if (!captchaValue.trim().equals(captcha.trim())) {
            return new ApiResponse<>(400, "验证码错误", new GenaralDataResponse(false, null));
        }
        // 验证成功后删除Redis Key（防止重复提交）
        try {
            stringRedisTemplate.delete(captchaKey);
        } catch (Exception e) {
            System.err.println("删除验证码Key失败：" + e.getMessage());
        }
        //用户不存在
        if(captchaValue.trim().equals(captcha.trim())
                &&userDaoImp.verifyUserExistenceService(user)==0){
            GenaralDataResponse data = new GenaralDataResponse(false,null);
            return new ApiResponse<>(404,"用户不存在",data);
        }
        //密码错误
        else if (captchaValue.trim().equals(captcha.trim())
                &&userDaoImp.verifyUserExistenceService(user)!=0
                &&userDaoImp.verifyUserPasswordService(user)==0) {
            GenaralDataResponse data = new GenaralDataResponse(false,null);
            return new ApiResponse<>(404,"密码错误",data);
        }
        //登录成功
        else if (captchaValue.trim().equals(captcha.trim())
                &&userDaoImp.verifyUserExistenceService(user)!=0
                &&userDaoImp.verifyUserPasswordService(user)!=0){
            String token = jwtUtil.generateToken(user.getUsername()); // 传入用户名生成令牌
            GenaralDataResponse data = new GenaralDataResponse(true,token);
            System.err.println("接口发送的token:"+token);
            return new ApiResponse<>(200,"登录成功",data);
        }
        GenaralDataResponse data = new GenaralDataResponse(false,null);
        return new ApiResponse<>(404,"未知的错误",data);
    }
}
