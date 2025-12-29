package com.news.backendproject.verify;

import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.entity.User;
import com.news.backendproject.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExistenceVerifyAccessJwt {
    private final JwtUtil jwtUtil;
    private final UserDaoImp userDaoImp;
    //判断返回值是否为null判断是否用户合法
    public User verifyAndReturnProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7).trim();
        String jwtUsername = jwtUtil.extractUsername(token);
        //提取到的用户名是否合法
        boolean validateResult = jwtUtil.validateToken(token, jwtUsername);
        //当前jwt提取出的用户名是否一致,且合法
        if (validateResult) {
            User userForSelect = new User();
            userForSelect.setUsername(jwtUsername);
            int i = userDaoImp.verifyUserExistenceService(userForSelect);
            if (i == 0) {
                //jwt的用户信息未被找到
                return null;
            }
            return userDaoImp.getUserInforService(userForSelect);
        }
        //jwt验证未通过
        return null;
    }
}
