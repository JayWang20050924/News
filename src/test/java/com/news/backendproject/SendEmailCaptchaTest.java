package com.news.backendproject;

import com.news.backendproject.service.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendEmailCaptchaTest {
    @Autowired
    private EmailService emailService;
    @Test
    public void test() throws Exception {
        emailService.sendCaptcha("wp18054404921@163.com","54ly88");
    }
}
