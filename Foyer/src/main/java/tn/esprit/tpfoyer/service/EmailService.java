package tn.esprit.tpfoyer.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.Foyer;

import java.util.Properties;

@Service
public class EmailService {

    public void sendFoyerCreationEmail(String to, Foyer foyer) throws MessagingException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Configuration SMTP
        mailSender.setHost("longevityplus.store");
        mailSender.setPort(465);
        mailSender.setUsername("votre_username"); // Remplacez par votre username
        mailSender.setPassword("votre_password"); // Remplacez par votre mot de passe

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // Seulement pour le développement

        // Création du message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("no-reply@longevityplus.store");
        helper.setTo(to);
        helper.setSubject("Nouveau foyer créé - " + foyer.getNomFoyer());

        // Corps du message HTML
        String content = "<html><body>"
                + "<h2>Nouveau foyer créé avec succès</h2>"
                + "<p><strong>Nom du foyer:</strong> " + foyer.getNomFoyer() + "</p>"
                + "<p><strong>Capacité:</strong> " + foyer.getCapaciteFoyer() + "</p>"
                + "<p>Date de création: " + new java.util.Date() + "</p>"
                + "</body></html>";

        helper.setText(content, true);

         mailSender.send(message);
    }
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@longevityplus.store");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}