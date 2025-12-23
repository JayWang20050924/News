package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.UserRegisterDto;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import com.news.backendproject.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public  class UserLoginService {
    private final JwtUtil jwtUtil;
    private final UserDaoImp userDaoImp;
    private final StringRedisTemplate stringRedisTemplate;

    public ApiResponse<GeneralDataResponse> userLogin(
            String username,
            String password,
            String captcha,
            String captchaKey

    ){
        UserRegisterDto user = new UserRegisterDto();
        user.setUsername(username);
        user.setPassword(password);
        // 从Redis读取验证码
        String redisKey = null;
        try {
            redisKey = stringRedisTemplate.opsForValue().get(captchaKey);
        } catch (Exception e) {
            return new ApiResponse<>(500, "服务器升级中稍后再试", new GeneralDataResponse(false, null));
        }

        // 验证码过期/不存在
        if (redisKey == null) {
            return new ApiResponse<>(400, "请重新获取验证码", new GeneralDataResponse(false, null));
        }

        // 验证码不匹配
        if (!redisKey.trim().equals(captcha.trim())) {
            return new ApiResponse<>(400, "验证码错误", new GeneralDataResponse(false, null));
        }
        // 验证成功后删除Redis Key
        try {
            stringRedisTemplate.delete(captchaKey);
        } catch (Exception e) {
            System.err.println("删除验证码Key失败：" + e.getMessage());
        }
        //用户不存在
        if(redisKey.trim().equals(captcha.trim())
                &&userDaoImp.verifyUserExistenceService(user)==0){
            GeneralDataResponse data = new GeneralDataResponse(false,null);
            return new ApiResponse<>(404,"用户不存在",data);
        }
        //密码错误
        else if (redisKey.trim().equals(captcha.trim())
                &&userDaoImp.verifyUserExistenceService(user)!=0
                &&userDaoImp.verifyUserPasswordService(user)==0) {
            GeneralDataResponse data = new GeneralDataResponse(false,null);
            return new ApiResponse<>(404,"密码错误",data);
        }
        //登录成功
        else if (redisKey.trim().equals(captcha.trim())
                &&userDaoImp.verifyUserExistenceService(user)!=0
                &&userDaoImp.verifyUserPasswordService(user)!=0){
            String token = jwtUtil.generateToken(user.getUsername()); // 传入用户名生成令牌
            GeneralDataResponse data = new GeneralDataResponse(true,token);
            System.err.println("接口发送的token:"+token);
            return new ApiResponse<>(200,"登录成功",data);
        }
        GeneralDataResponse data = new GeneralDataResponse(false,null);
        return new ApiResponse<>(404,"未知的错误",data);
    }
}
