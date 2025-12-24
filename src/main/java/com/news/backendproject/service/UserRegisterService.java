package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.UserRegisterDto;
import com.news.backendproject.entity.User;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import com.news.backendproject.verify.EmailCaptchaVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserDaoImp userDaoImp;
    private final EmailCaptchaVerify emailCaptchaVerify;
    public ApiResponse<GeneralDataResponse> userRegister(UserRegisterDto dto, String redisKey) {
        String emailCaptcha=dto.getEmailCaptcha();
        ApiResponse<GeneralDataResponse> verify = emailCaptchaVerify.verify(emailCaptcha, redisKey);
        if (verify.getCode()==200){
            int i = userDaoImp.addUserService(dto);
            if (i!=0){
                verify.setMsg("注册成功");
                return verify;
            }
            return new ApiResponse<>(500,"服务器升级中稍后再试",new GeneralDataResponse(false,null));
        }
        return verify;
    }
}
