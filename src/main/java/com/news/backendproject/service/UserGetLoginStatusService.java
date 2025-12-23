package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.UserRegisterDto;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDataResponse;
import com.news.backendproject.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGetLoginStatusService {
    private final JwtUtil jwtUtil;
    private final UserDaoImp userDaoImp;

    public ApiResponse<GeneralDataResponse> getLoginStatus(HttpServletRequest request){
        // 读取请求头中的 token
        String authHeader = request.getHeader("Authorization");
        String currentUsername = null;
        // 如果有 token，尝试验证
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                currentUsername = jwtUtil.extractUsername(token); // 解析成功即代表令牌有效
                UserRegisterDto user = new UserRegisterDto();//新建user类进行用户存在性判断
                user.setUsername(currentUsername);
                if(userDaoImp.verifyUserExistenceService(user)!=0){
                    GeneralDataResponse data = new GeneralDataResponse(true, null);
                    return new ApiResponse<GeneralDataResponse>(200, "已登录", data);
                }
                GeneralDataResponse data = new GeneralDataResponse(false, null);
                return new ApiResponse<GeneralDataResponse>(401, "用户失效", data);
            } catch (JwtException e) {
                // 解析失败（签名无效/过期等），视为未登录
                GeneralDataResponse data = new GeneralDataResponse(false, null);
                return new ApiResponse<GeneralDataResponse>(401, "未登录", data);
            }
        }
        return new ApiResponse<>(401,"未登录",new GeneralDataResponse(false, null));
    }
}
