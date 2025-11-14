package com.news.backendproject.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Cookie 工具类：封装 Cookie 的设置、获取、删除操作
 */
public class CookieUtil {
    public static void setCookie(HttpServletResponse response, String cookiename, String cookievalue,int days,boolean secure){
        Cookie cookie = new Cookie(cookiename,cookievalue);
        cookie.setMaxAge(days * 24 * 60 * 60);
        cookie.setPath("/");
        cookie.setSecure(secure); // 仅 HTTPS 传输（生产环境强制开启）
        cookie.setHttpOnly(false); // 允许前端 JS 读取（否则前端无法获取）
        response.addCookie(cookie);
    }
    public static void deleteCookie(HttpServletResponse resp, String cookiename){
        Cookie cookie = new Cookie(cookiename,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }
}
