package com.news.backendproject.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionSetTimeoutPeriodUtil {
    //禁止创建实例
    private SessionSetTimeoutPeriodUtil() {

    }
    // session超时标识
    private static final String SESSION_TIMEOUT_SET_FLAG = "SESSION_TIMEOUT_SET_FLAG";
    public static String set(HttpServletRequest request, int timeoutPeriod){
        if (request == null) {
            throw new IllegalArgumentException("HttpServletRequest不能为null");
        }
        if (timeoutPeriod <= 0) {
            throw new IllegalArgumentException("超时时长必须大于0");
        }
        HttpSession session = request.getSession(true);
        if (session.getAttribute(SESSION_TIMEOUT_SET_FLAG) == null) {
            // 设置Session过期时间（24小时）
            session.setMaxInactiveInterval(3600 * timeoutPeriod);
            // 添加标记
            session.setAttribute(SESSION_TIMEOUT_SET_FLAG, Boolean.TRUE);
        }
        return session.getId();
    }
}
