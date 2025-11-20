package com.news.backendproject.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * Cookie 工具类：修改为秒级过期时间，适配短期有效期场景
 */
@Component
public class CookieUtil {
    // 调整参数：将 days 改为 maxAgeSeconds（单位：秒）
    public static void setCookie(
            HttpServletResponse response,
            String cookieName,
            String cookieValue,
            int maxAgeSeconds,  // 过期时间（秒）
            boolean secure,
            boolean httpOnly) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAgeSeconds); // 直接设置秒数（30分钟=1800秒）
        cookie.setPath("/"); // 全站可见
        cookie.setSecure(secure); // 开发环境(如vite)HTTP设为false，生产测试(nginx)HTTPS设为true
        cookie.setHttpOnly(httpOnly); // true：禁止前端JS读取，防XSS
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletResponse resp, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0); // 立即过期
        cookie.setPath("/");
        resp.addCookie(cookie);
    }
}