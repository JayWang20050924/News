package com.news.backendproject.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 邮件发送核心服务
 */
@Service
public class EmailService {

    // 注入Spring Boot自动配置的邮件发送器
    @Autowired
    private JavaMailSender javaMailSender;

    // 发送方邮箱（与配置文件中的spring.mail.username一致）
    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * 发送邮箱验证码（HTML格式）
     * @param toEmail 接收方邮箱
     * @param code 验证码
     */
    public boolean sendCaptcha(String toEmail, String code){
        try{
        // 1. 创建MimeMessage对象（支持HTML、附件）
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // 2. 使用MimeMessageHelper简化配置（true表示支持多部分内容，UTF-8解决中文乱码）
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // 3. 设置邮件基本信息
        helper.setFrom(fromEmail); // 发送方
        helper.setTo(toEmail);     // 接收方
        helper.setSubject("【环球新闻】邮箱验证码"); // 邮件主题

        // 4. 构建HTML格式的邮件内容
        String emailContent = String.format(
                "<div style='font-family: Arial, sans-serif;'>" +
                        "<h3>您好！</h3>" +
                        "<p>您的邮箱验证码为：<span style='color: #ff0000; font-size: 18px; font-weight: bold;'>%s</span></p>" +
                        "<p>该验证码有效期为5分钟，请及时验证。</p>" +
                        "<p>如非本人操作，请忽略此邮件。</p>" +
                        "</div>", code
        );
        // 第二个参数为true，表示内容是HTML格式
        helper.setText(emailContent, true);
        // 5. 发送邮件
        javaMailSender.send(mimeMessage);
        return true;
        }catch (MessagingException e){
            e.printStackTrace();
            return false;
        }
    }
}