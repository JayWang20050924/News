package com.news.backendproject;

import com.news.backendproject.utils.RadomCaptchaUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CaptchaGenerateTest {
    @Test
    public void generateCaptcha() {
        System.out.println(RadomCaptchaUtil.generate8DigitCaptcha());
    }
}
