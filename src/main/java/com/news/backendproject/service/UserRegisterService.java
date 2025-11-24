package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.domain.User;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GenaralDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterService {
    @Autowired
    private UserDaoImp userDaoImp;
    public ApiResponse<GenaralDataResponse> userRegister(User user) {
        GenaralDataResponse data = new GenaralDataResponse(true,null);
        return new ApiResponse<>(200,"注册成功",data);}
}
