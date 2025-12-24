package com.news.backendproject.verify;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dao.UserDao;
import com.news.backendproject.dto.SendEmailCaptchaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExistenceVerify {
    private final UserDaoImp userDaoImp;
    public boolean usernameExistenceVerify(SendEmailCaptchaDTO dto) {
        return false;
    }

    public boolean emailExistenceVerify(SendEmailCaptchaDTO dto) {
        return false;
    }
}
