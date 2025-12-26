package com.news.backendproject.service;

import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGetSelfProfileService {
    public ApiResponse<GeneralDataResponse> getSelfProfile() {
        return new ApiResponse<>(400,"访问getSelfProfile函数成功",new GeneralDataResponse(false,null));
    }
}
