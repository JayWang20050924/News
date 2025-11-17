package com.news.backendproject.service;

import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.LoginStatusResponse;
import com.news.backendproject.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class UserGetLoginStatusService {
    private final JwtUtil jwtUtil;

    public UserGetLoginStatusService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public ApiResponse<LoginStatusResponse> getLoginStatus(HttpServletRequest request){
        // 手动读取请求头中的 token
        String authHeader = request.getHeader("Authorization");
        String currentUsername = null;
        boolean isLogin=false;
        // 如果有 token，尝试验证
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                currentUsername = jwtUtil.extractUsername(token); // 手动验证 token
                System.err.println("currentUsername（手动验证）:" + currentUsername);
                LoginStatusResponse data = new LoginStatusResponse(!isLogin, null);
                return new ApiResponse<>(200,"已登录",data);
            } catch (JwtException e) {
                // token 无效，忽略（视为未登录）
                LoginStatusResponse data = new LoginStatusResponse(isLogin, null);
                System.err.println("token 无效：" + e.getMessage());
                return new ApiResponse<>(401,"未登录",data);
            }
        }
        //没有token
        LoginStatusResponse data = new LoginStatusResponse(isLogin, null);
        System.err.println("无token");
        return new ApiResponse<>(401,"未登录",data);
    }
}
