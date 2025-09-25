package com.vechetchuo.Ledgerly.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    //https://myaccount.google.com/security
    //https://myaccount.google.com/apppasswords

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetLink(String to, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset");
        message.setText("Click to reset: " + link);
        mailSender.send(message);
    }
}
