package aarcosb.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String fromEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("aarcos664@gmail.com");
        message.setFrom(fromEmail);
        message.setSubject(subject);
        message.setText("From: " + fromEmail + "\n\n" + body);

        mailSender.send(message);
    }
}
