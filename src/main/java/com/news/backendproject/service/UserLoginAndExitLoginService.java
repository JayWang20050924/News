package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.entity.User;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import com.news.backendproject.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public  class UserLoginAndExitLoginService {
    private final JwtUtil jwtUtil;
    private final UserDaoImp userDaoImp;
    private final StringRedisTemplate stringRedisTemplate;

    public ApiResponse<GeneralDataResponse> userLogin(
            String username,
            String password,
            String captcha,
            String redisKey

    ){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        // 从Redis读取验证码
        String redisValue = null;
        redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        // 验证码过期/不存在
        if (redisValue == null) {
            return new ApiResponse<>(400, "请重新获取验证码", new GeneralDataResponse(false, null));
        }
        // 验证码不匹配
        if (!redisValue.trim().equals(captcha.trim())) {
            return new ApiResponse<>(400, "验证码错误", new GeneralDataResponse(false, null));
        }
        // 验证成功后删除Redis Key
        stringRedisTemplate.delete(redisKey);
        //用户不存在
        if(redisValue.trim().equals(captcha.trim())
                &&userDaoImp.verifyUserExistenceService(user)==0){
            GeneralDataResponse data = new GeneralDataResponse(false,null);
            return new ApiResponse<>(404,"用户不存在",data);
        }
        //密码错误
        else if (redisValue.trim().equals(captcha.trim())
                &&userDaoImp.verifyUserExistenceService(user)!=0
                &&userDaoImp.verifyUserPasswordService(user)==0) {
            GeneralDataResponse data = new GeneralDataResponse(false,null);
            return new ApiResponse<>(404,"密码错误",data);
        }
        //登录成功
        else if (redisValue.trim().equals(captcha.trim())
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
    //用户退出登录
    public ApiResponse<GeneralDataResponse> userExitLogin(String token){
        return new ApiResponse<>(200,"登出成功",new GeneralDataResponse(true,null));
    }
}
