package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.domain.User;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GeneralDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserDaoImp userDaoImp;

    public ApiResponse<GeneralDataResponse> userRegister(User user) {
        return new ApiResponse<>(400,"注册失败",new GeneralDataResponse(false,null));
    }
}
