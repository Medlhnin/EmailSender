package com.example.EmailSender.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String email;

    public void sendEmail(String name, String email, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom(email);
        helper.setTo("lahnin.010@gmail.com");
        helper.setSubject(subject);

        String htmlMsg = "<p><strong>Nom :</strong> " + name + "</p>" +
                "<p><strong>Email :</strong> " + email + "</p>" +
                "<p><strong>Message :</strong><br>" + message + "</p>";

        helper.setText(htmlMsg, true);

        mailSender.send(mimeMessage);
    }


    // Envoi d'un email automatique de confirmation
    public void sendConfirmationEmail(String to, String userName, String response) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(email);
        helper.setTo(to);
        helper.setSubject("Confirmation de réception de votre message");
        helper.setText("<p>Bonjour <strong>" + userName + "</strong>,</p>" + response, true);

        mailSender.send(mimeMessage);
    }
}
