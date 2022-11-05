package com.dan.cosmos.service;

import com.dan.cosmos.model.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSenderImpl mailSender;
    private final String baseUrl;

    public EmailService(JavaMailSenderImpl mailSender, @Value("${BASE_URL}") String baseUrl) {
        this.mailSender = mailSender;
        this.baseUrl = baseUrl;
    }

    public void sendConfirmEmail(AppUser appUser) {
        String confirmUrl = String.format("%sconfirmemail?token=%s", baseUrl, appUser.getEmailConfirmUUID());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cosmosapp");
        message.setTo(appUser.getEmail());
        message.setSubject("Email confirmation");
        message.setText(String.format("Hello, %s! Please, confirm your email %s", appUser.getUsername(), confirmUrl));
        mailSender.send(message);

    }

    public void sendPasswordRecoveryEmail(AppUser appUser) {
        String recoveryUrl = String.format("%spasswordreset/%s", baseUrl, appUser.getPasswordRecoveryToken());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cosmosapp");
        message.setTo(appUser.getEmail());
        message.setSubject("Password recovery");
        message.setText(String.format("Hello, %s! Еo recover your password follow the link  %s", appUser.getUsername(), recoveryUrl));
        mailSender.send(message);
    }
}
