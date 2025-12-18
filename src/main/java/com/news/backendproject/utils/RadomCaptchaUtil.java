package com.news.backendproject.utils;
import java.util.Random;

public class RadomCaptchaUtil {
    /**
     * 生成6位数字验证码（常用）
     */
    public static String generate6DigitCaptcha() {
        Random random = new Random();
        // 生成100000~999999的随机数，确保是6位
        return String.format("%06d", random.nextInt(900000) + 100000);
    }

    /**
     * 生成指定长度的验证码（数字+大小写字母）
     */
    public static String generateCaptcha(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
