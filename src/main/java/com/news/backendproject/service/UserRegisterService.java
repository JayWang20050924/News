package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.domain.User;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GeneralDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterService {
    @Autowired
    private UserDaoImp userDaoImp;
    public ApiResponse<GeneralDataResponse> userRegister(User user) {
        return new ApiResponse<>(400,"注册失败",new GeneralDataResponse(false,null));
    }
}
