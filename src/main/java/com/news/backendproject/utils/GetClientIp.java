package com.news.backendproject.utils;

import jakarta.servlet.http.HttpServletRequest;


public class GetClientIp {
    private GetClientIp(){
        throw new UnsupportedOperationException("工具类不能实例化");
    }
    public static String get(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多IP场景（X-Forwarded-For可能返回多个IP，取第一个）
        return ip != null ? ip.split(",")[0].trim() : "unknown";
    }
}
