package com.news.backendproject.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha kaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties props = new Properties();

        // 验证码字符集：数字+大小写字母
        props.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        // 验证码长度：4位
        props.setProperty("kaptcha.textproducer.char.length", "4");
        // 图片尺寸：100px * 40px
        props.setProperty("kaptcha.image.width", "100");
        props.setProperty("kaptcha.image.height", "40");
        // 文字颜色
        props.setProperty("kaptcha.textproducer.font.color", "black");
        // 文字大小
        props.setProperty("kaptcha.textproducer.font.size", "24");
        // 干扰线颜色
        props.setProperty("kaptcha.noise.color", "gray");
        // 背景渐变（可选）
        props.setProperty("kaptcha.background.clear.from", "white");
        props.setProperty("kaptcha.background.clear.to", "lightGray");

        kaptcha.setConfig(new Config(props));
        return kaptcha;
    }
}
