package com.news.backendproject.controller.userApi;

import com.news.backendproject.entity.LoginStatusResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetLoginStatus {
    @RequestMapping("/GetLoginStatus" )
    //springboot自动注入session
    public LoginStatusResponse getStatus(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean isLogin=false;
        isLogin= session.getAttribute("username") != null;
        return new LoginStatusResponse(isLogin);
    }
}
