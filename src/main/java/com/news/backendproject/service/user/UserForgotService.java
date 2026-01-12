package com.news.backendproject.service.user;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDto;
import com.news.backendproject.dto.user.UserForgotDto;
import com.news.backendproject.entity.User;
import com.news.backendproject.verify.EmailCaptchaVerify;
import com.news.backendproject.verify.ExistenceVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserForgotService {
    private final ExistenceVerify existenceVerify;
    private final EmailCaptchaVerify emailCaptchaVerify;
    private final UserDaoImp userDaoImp;

    public ApiResponse<GeneralDto> forgot(UserForgotDto dto, String redisKey){
        if (dto.getUsername().equals(dto.getPasswordReset())){
            return new ApiResponse<>(409,"新密码不能与用户名相同",new GeneralDto(false,null));
        }

        if (!dto.getPasswordReset().equals(dto.getConfirmPwdReset())) {
            return new ApiResponse<>(409,"两次密码不一致",new GeneralDto(false,null));
        }
        if (!existenceVerify.usernameExistenceVerify(dto)){
            return new ApiResponse<>(409,"用户不存在",new GeneralDto(false,null));
        }
        //是否用于找回密码的对象不存在
        if (existenceVerify.emailExistenceVerifyForgot(dto)){
            return new ApiResponse<>(409,"无效的密保邮箱",new GeneralDto(false,null));
        }

        String emailCaptcha = dto.getEmailCaptcha();
        ApiResponse<GeneralDto> verify = emailCaptchaVerify.verify(emailCaptcha, redisKey);

        if (verify.getCode()==200){
            User user = new User();
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPasswordReset());
            int i = userDaoImp.updatePasswordInforByUsernameAndEmailService(user);
            if (i!=0){
                verify.setMsg("密码重置成功");
                return verify;
            }
            return new ApiResponse<>(409,"检查是否与旧密码重复",new GeneralDto(false,null));
        }
        return verify;
    }
}
