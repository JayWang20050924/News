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
    public User verify(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7).trim();
        String jwtUsername = jwtUtil.extractUsername(token);
        User userForSelect = new User();
        userForSelect.setUsername(jwtUsername);
        return userDaoImp.getUserInforService(userForSelect);
    }
}
