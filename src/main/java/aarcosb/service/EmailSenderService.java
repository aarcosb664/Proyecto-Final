package aarcosb.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private JavaMailSender mailSender;

    // Envía un email
    public void sendEmail(String fromEmail, String subject, String body) {
        // Crea un mensaje de email
        SimpleMailMessage message = new SimpleMailMessage();
        // Establece el destinatario
        message.setTo("aarcos664@gmail.com");
        // Establece el remitente
        message.setFrom(fromEmail);
        // Establece el asunto
        message.setSubject(subject);
        // Establece el cuerpo del email
        message.setText("From: " + fromEmail + "\n\n" + body);

        // Envía el email
        mailSender.send(message);
    }
}
