package com.news.backendproject.verify;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.user.ExistenceVerifyDto;
import com.news.backendproject.po.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExistenceVerify {
    private final UserDaoImp userDaoImp;
        public boolean usernameExistenceVerify(ExistenceVerifyDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        int i = userDaoImp.verifyUserExistenceService(user);
        return i != 0;
    }

    public boolean emailExistenceVerifyRegister(ExistenceVerifyDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        int i = userDaoImp.verifyEmailExistenceRegisterService(user);
        return i != 0;
    }
    //返回值为是否用于找回密码的对象不存在
    public boolean emailExistenceVerifyForgot(ExistenceVerifyDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        int i = userDaoImp.verifyEmailExistenceForgotService(user);
        return i == 0;
    }
}
