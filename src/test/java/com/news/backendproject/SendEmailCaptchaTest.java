package com.news.backendproject;

import com.news.backendproject.service.EmailGenerateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendEmailCaptchaTest {
    @Autowired
    private EmailGenerateService emailGenerateService;
    @Test
    public void test() throws Exception {
        emailGenerateService.sendCaptcha("wp18054404921@163.com","ly45son","register");
    }
}
