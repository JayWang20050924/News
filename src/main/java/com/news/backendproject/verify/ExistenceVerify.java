package com.news.backendproject.verify;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dao.UserDao;
import com.news.backendproject.dto.SendEmailCaptchaDTO;
import com.news.backendproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExistenceVerify {
    private final UserDaoImp userDaoImp;
    public boolean usernameExistenceVerify(SendEmailCaptchaDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        int i = userDaoImp.verifyUserExistenceService(user);
        return i != 0;
    }

    public boolean emailExistenceVerify(SendEmailCaptchaDTO dto) {
        User user = new User();
        user.setEmail(dto.getToEmail());
        int i = userDaoImp.verifyEmailExistenceService(user);
        return i != 0;
    }
}
