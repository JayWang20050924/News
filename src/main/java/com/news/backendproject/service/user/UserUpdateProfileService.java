package com.news.backendproject.service.user;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDto;
import com.news.backendproject.dto.UserProfileDto;
import com.news.backendproject.entity.User;
import com.news.backendproject.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdateProfileService {
    private  final UserDaoImp userDaoImp;
    private final JwtUtil jwtUtil;
    public ApiResponse<GeneralDto> updateProfile(UserProfileDto dto, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7).trim();
        String jwtUsername = jwtUtil.extractUsername(token);

        User userForUpdateProfile = new User();
        //使用jwt令牌中的username
        userForUpdateProfile.setUsername(jwtUsername);
        userForUpdateProfile.setGender(dto.getGender());
        userForUpdateProfile.setAddress(dto.getAddress());
        userForUpdateProfile.setBirthday(dto.getBirthday());
        //如果username被修改过,jwt会报错
        int i = userDaoImp.updateBasicInforByUsernameAndEmailService(userForUpdateProfile);
        if (i!=0){
            return new ApiResponse<>(200,"修改成功",new GeneralDto(true,null));
        }
        return new ApiResponse<>(400,"修改失败",new GeneralDto(true,null));
    }
}
