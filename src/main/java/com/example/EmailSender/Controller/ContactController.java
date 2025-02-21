package com.example.EmailSender.Controller;

import com.example.EmailSender.Service.EmailService;
import com.example.EmailSender.Service.OpenAIService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin("*")
public class ContactController {
    @Autowired
    private OpenAIService openAIService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String email = request.get("email");
            String subject = request.get("subject");
            String message = request.get("message");

            if (name.isEmpty() || email.isEmpty() || subject.isEmpty() || message.isEmpty()) {
                return "Tous les champs sont requis.";
            }

            emailService.sendEmail(name, email, subject, message);
            String response = openAIService.generateText(message);

            // Envoi de l'email de confirmation à l'utilisateur
            emailService.sendConfirmationEmail(email, name, response);
            return "Votre message a été envoyé avec succès.";
        } catch (MessagingException e) {
            return "Erreur lors de l'envoi du message: " + e.getMessage();
        }
    }
}
