package com.news.backendproject.service.user;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.GeneralDto;
import com.news.backendproject.dto.user.UserProfileDto;
import com.news.backendproject.po.User;
import com.news.backendproject.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class UserUpdateProfileService {
    private final UserDaoImp userDaoImp;
    private final JwtUtil jwtUtil;

    public ApiResponse<GeneralDto> updateProfile(UserProfileDto dto, HttpServletRequest request) {
        // 校验生日是否为未来日期
        if (dto.getBirthday() != null) {
            try {
                // 获取当前日期（仅年月日）
                LocalDate today = LocalDate.now();

                //如果生日日期在当前日期之后（未来日期），抛出 DateTimeParseException
                if (dto.getBirthday().isAfter(today)) {
                    throw new DateTimeParseException(
                            "生日不能是未来的日期",  // 异常提示信息
                            dto.getBirthday().toString(),       // 出错的日期字符串
                            0                         // 错误位置（0 表示整体）
                    );
                }
            } catch (DateTimeParseException e) {
                // 捕获解析异常（如格式错误）或未来日期异常，直接抛出
                throw e;
            }
        }
        // ========== 日期校验结束 ==========

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
        if (i != 0) {
            return new ApiResponse<>(200, "修改成功", new GeneralDto(true, null));
        }
        return new ApiResponse<>(400, "修改失败", new GeneralDto(true, null));
    }
}