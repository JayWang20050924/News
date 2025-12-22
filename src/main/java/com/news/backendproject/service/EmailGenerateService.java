package com.news.backendproject.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 邮件发送核心服务
 */
@Service
@RequiredArgsConstructor
public class EmailGenerateService {
    private final JavaMailSender javaMailSender;

    // 发送方邮箱
    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * 发送邮箱验证码（HTML格式）
     * @param toEmail 接收方邮箱
     * @param code 验证码
     */
    public boolean sendCaptcha(String toEmail, String code,String operationType){
        String oT = switch (operationType) {
            case "register" -> "注册操作";
            case "resetPassword" -> "重置密码操作";
            case "forgot" -> "找回密码操作";
            default -> "";
        };
        try{
        // 创建MimeMessage对象（支持HTML、附件）
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // 操作代理——MimeMessageHelper
        // 后续通过helper调用的setFrom()、setTo()、setText()等所有方法
        // 最终都会同步修改这个绑定的mimeMessage对象的属性
        // 使用MimeMessageHelper简化配置（true表示支持多部分内容，UTF-8解决中文乱码）
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // 设置邮件基本信息
        helper.setFrom(fromEmail); // 发送方
        helper.setTo(toEmail);     // 接收方
        helper.setSubject(String.format("【环球新闻】%s邮箱验证码",oT)); // 邮件主题

        // 构建HTML格式的邮件内容
        String emailContent = String.format(
                "<div style='font-family: Arial, sans-serif;'>" +
                        "<h3>您好</h3>" +
                        "<p>您正在进行<span style='color: #0066cc; font-weight: bold;'>%s</span>，您的邮箱验证码为：</p>"+
                        "<p><span style='color: #ff0000; font-size: 18px; font-weight: bold;'>%s</span></p>" +
                        "<p>该验证码有效期为10分钟，请及时验证。</p>" +
                        "<p>如非本人操作，请忽略此邮件。</p>" +
                        "</div>",
                        oT,
                        code
        );
        // 第二个参数为true，表示内容是HTML格式
        helper.setText(emailContent, true);
        // 发送邮件
        javaMailSender.send(mimeMessage);
        return true;
        }catch (MessagingException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}