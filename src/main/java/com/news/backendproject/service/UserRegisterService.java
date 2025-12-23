package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.UserRegisterDto;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import com.news.backendproject.verify.EmailCaptchaVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserDaoImp userDaoImp;
    private final EmailCaptchaVerification emailCaptchaVerification;
    public ApiResponse<GeneralDataResponse> userRegister(UserRegisterDto user, String redisKey) {
        String emailCaptcha=user.getEmailCaptcha();
        ApiResponse<GeneralDataResponse> verify = emailCaptchaVerification.verify(emailCaptcha, redisKey);
        if (verify.getCode()==200){
            int i = userDaoImp.addUserService(user);
            if (i!=0){
                verify.setMsg("注册成功");
                return verify;
            }
            return new ApiResponse<>(500,"服务器升级中稍后再试",new GeneralDataResponse(false,null));
        }
        return verify;
    }
}
