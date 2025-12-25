package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import com.news.backendproject.dto.SendEmailCaptchaDTO;
import com.news.backendproject.entity.User;
import com.news.backendproject.utils.RadomCaptchaUtil;
import com.news.backendproject.verify.ExistenceVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * sendRegister用于在用户注册时发送验证码
 * sendForgot用于在用户找回密码时发送验证码
 */

@Service
@RequiredArgsConstructor
public class SendEmailCaptchaService {
    private final EmailGenerateService emailGenerateService;
    private final StringRedisTemplate stringRedisTemplate;
    private final ExistenceVerify existenceVerify;
    //用于注册时发送邮箱验证码
    public ApiResponse<GeneralDataResponse> sendRegister(SendEmailCaptchaDTO dto, String redisKey) {
        //用户名是否重复
        if (existenceVerify.usernameExistenceVerify(dto)){
            return new ApiResponse<>(409,"当前账户被注册了,换一个吧~",new GeneralDataResponse(false,null));
        }
        //邮箱是否被绑定过
        if (existenceVerify.emailExistenceVerify(dto)){
            return new ApiResponse<>(409,"当前邮箱被绑定了,换一个吧~",new GeneralDataResponse(false,null));
        }
        String emailCaptchaGenerated=RadomCaptchaUtil.generate8DigitCaptcha();

        boolean result= emailGenerateService.sendCaptcha(dto.getToEmail(),emailCaptchaGenerated,dto.getOperationType());
        if (result){
            //发送成功后存储验证码到redis
            stringRedisTemplate.opsForValue().set(
                    redisKey,
                    emailCaptchaGenerated,
                    600,
                    TimeUnit.SECONDS
            );
            return new ApiResponse<>(200,"发送成功",new GeneralDataResponse(true,null));
        }
        return new ApiResponse<>(500,"邮箱验证码发送失败",new GeneralDataResponse(false,null));
    }

    //用于忘记密码时发送邮箱验证码
    public ApiResponse<GeneralDataResponse> sendForgotPwd(SendEmailCaptchaDTO dto, String redisKey){
        //用户是否存在
        if (!existenceVerify.usernameExistenceVerify(dto)){
            return new ApiResponse<>(409,"用户不存在",new GeneralDataResponse(false,null));
        }
        //邮箱是否被绑定过
        if (!existenceVerify.emailExistenceVerify(dto)){
            return new ApiResponse<>(409,"无效的密保邮箱",new GeneralDataResponse(false,null));
        }
        String emailCaptchaGenerated=RadomCaptchaUtil.generate8DigitCaptcha();
        boolean result= emailGenerateService.sendCaptcha(dto.getToEmail(),emailCaptchaGenerated,dto.getOperationType());
        if (result){
            //发送成功后存储验证码到redis
            stringRedisTemplate.opsForValue().set(
                    redisKey,
                    emailCaptchaGenerated,
                    600,
                    TimeUnit.SECONDS
            );
            return new ApiResponse<>(200,"发送成功",new GeneralDataResponse(true,null));
        }
        return new ApiResponse<>(500,"邮箱验证码发送失败",new GeneralDataResponse(false,null));
    }
}
