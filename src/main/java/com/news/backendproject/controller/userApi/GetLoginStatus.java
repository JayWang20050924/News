package com.news.backendproject.controller.userApi;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetLoginStatus {
    @RequestMapping("/GetLoginStatus" )
    //springboot自动注入session
    public LoginStatusResponse getStatus(HttpSession session){
        boolean isLogin=false;
        isLogin= session.getAttribute("username") != null;
        return new LoginStatusResponse(isLogin);
    }
    static record LoginStatusResponse(boolean isLogin){}
}
