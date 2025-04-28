package com.example.Ranking_Resume.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendActivationEmail(String toEmail, String activationCode) {
        String subject = "Activate your account";
        String message = "Click the link to activate: http://localhost:8080/api/auth/activate?code=" + activationCode;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("welcome2bunny@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
