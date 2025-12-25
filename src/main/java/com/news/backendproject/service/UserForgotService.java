package com.news.backendproject.service;

import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import com.news.backendproject.dto.UserForgotDto;
import com.news.backendproject.verify.ExistenceVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserForgotService {
    private final ExistenceVerify existenceVerify;
    public ApiResponse<GeneralDataResponse> forgot(UserForgotDto dto, String redisKey){
        if (!dto.getPasswordReset().equals(dto.getConfirmPwdReset())) {
            return new ApiResponse<>(409,"两次密码不一致",new GeneralDataResponse(false,null));
        }
        if (!existenceVerify.usernameExistenceVerify(dto)){
            return new ApiResponse<>(409,"用户不存在",new GeneralDataResponse(false,null));
        }
        if (!existenceVerify.emailExistenceVerify(dto)){
            return new ApiResponse<>(409,"错误的密保邮箱",new GeneralDataResponse(false,null));
        }
        return null;
    }
}
