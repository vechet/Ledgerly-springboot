package com.vechetchuo.Ledgerly.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    //https://myaccount.google.com/security
    //https://myaccount.google.com/apppasswords

    @Autowired
    private JavaMailSender mailSender;

//    public void sendResetLink(String to, String link) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Password Reset");
//        message.setText("Click to reset: " + link);
//        mailSender.send(message);
//    }

    public void sendResetLink(String email, String username, String link) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Reset Your Password");

            String htmlContent = String.format(
                    "<html><body style='font-family: Segoe UI, sans-serif; background-color: #f4f6f8; padding: 20px;'>"
                    + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.05);'>"
                    + "<h2 style='text-align: center; color: #333;'>Reset Your Password</h2>"
                    + "<p>Hello, %s</p>"
                    + "<p>We received a request to reset your password. Click the button below to set a new one:</p>"
                    + "<p style='text-align: center;'>"
                    + "<a href='%s' style='display: inline-block; padding: 14px 24px; background-color: #0078d4; color: white; text-decoration: none; border-radius: 6px; font-weight: bold;'>Reset Password</a>"
                    + "</p>"
                    + "<p>If you didn’t request this, you can safely ignore this email.</p>"
                    + "<div style='margin-top: 30px; font-size: 12px; color: #999; text-align: center;'>&copy; 2025 Ledgerly. All rights reserved.</div>"
                    + "</div></body></html>",
                    username, link
            );

            helper.setText(htmlContent, true); // true = HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send reset email", e);
        }
    }

}
