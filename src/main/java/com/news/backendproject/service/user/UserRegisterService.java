package com.news.backendproject.service.user;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.user.UserRegisterDto;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDto;
import com.news.backendproject.verify.EmailCaptchaVerify;
import com.news.backendproject.verify.ExistenceVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserDaoImp userDaoImp;
    private final EmailCaptchaVerify emailCaptchaVerify;
    private final ExistenceVerify existenceVerify;


    public ApiResponse<GeneralDto> userRegister(UserRegisterDto dto, String redisKey) {
        if (dto.getUsername().equals(dto.getPassword())){
            return new ApiResponse<>(409,"用户名与密码不能相同",new GeneralDto(false,null));
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return new ApiResponse<>(409,"两次密码不一致",new GeneralDto(false,null));
        }
        if (existenceVerify.usernameExistenceVerify(dto)){
            return new ApiResponse<>(409,"当前账户被注册了,换一个吧~",new GeneralDto(false,null));
        }
        if (existenceVerify.emailExistenceVerifyRegister(dto)){
            return new ApiResponse<>(409,"当前邮箱被绑定了,换一个吧~",new GeneralDto(false,null));
        }
        String emailCaptcha=dto.getEmailCaptcha();
        ApiResponse<GeneralDto> verify = emailCaptchaVerify.verify(emailCaptcha, redisKey);
        if (verify.getCode()==200){
            int i = userDaoImp.addUserService(dto);
            if (i!=0){
                verify.setMsg("注册成功");
                return verify;
            }
            return new ApiResponse<>(500,"密码重置失败",new GeneralDto(false,null));
        }
        return verify;
    }
}
