package com.tinqinacademy.authentication.core.utils;

import com.tinqinacademy.authentication.api.exceptions.customExceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.application.name}")
    private String emailSender;
    private final JavaMailSender javaMailSender;
    public void sendEmailForAccountActivation(String userFirstName, String toEmail, String randomGeneratedCode) throws EmailException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailSender);
            helper.setTo(toEmail);
            helper.setSubject("Registration confirmation");

            String htmlMsg = "<h2>Hello, " + userFirstName + "!</h2>" +
                    "<p>Thank you for registering!</p>" +
                    "<p>Please confirm your email by sending us this code: <span style='color:blue;'>" + randomGeneratedCode + "</span></p>" +
                    "<p>Once confirmed, your account will be activated.</p>" +
                    "<p>Best regards,<br>The Hotel Service Team</p>";
            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException();
        }
    }
    public void sendEmailWithNewPassword(String userFirstName, String toEmail, String newRandomGeneratedPassword) throws EmailException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailSender);
            helper.setTo(toEmail);
            helper.setSubject("New password");

            String htmlMsg = "<h2>Dear " + userFirstName + "!</h2>" +
                    "<p>You receive this email, because you have recently sent a request for password recovery.</p>" +
                    "<p><b>This is your new password: <span style='color:blue;'>" + newRandomGeneratedPassword+ "</span></b></p>" +
                    "<p>Change your password as soon as you gain access to your account!</p>";
            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException();
        }
    }

}
