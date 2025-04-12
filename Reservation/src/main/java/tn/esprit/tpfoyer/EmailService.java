package tn.esprit.tpfoyer;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail ;
    private String recipientName = "karim";

    @Async
    public void sendEmail(String to, String subject, boolean reminder) throws MessagingException, IOException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress(senderEmail));
        helper.setTo(to);
        message.setSubject(subject);

        Resource resource = new ClassPathResource("templates/emailTemplate.html");
        String template = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String htmlContent;
        ClassPathResource image;
        if (reminder) {
            image = new ClassPathResource("reminder.png");
            htmlContent = template
                    .replace("{{Heading}}", "Reminder: Action Required ⚠️")
                    .replace("{{name}}", recipientName)
                    .replace("{{msg1}}", "Your reservation is")
                    .replace("{{msg2}}", "not confirmed yet.")
                    .replace("{{msg3}}", "Please make your payment at your earliest convenience to secure your spot.");
        } else {
            image = new ClassPathResource("reserved.png");
            htmlContent = template
                    .replace("{{Heading}}", "Your Reservation is Completed 🎉")
                    .replace("{{name}}", recipientName)
                    .replace("{{msg1}}", "Your reservation has been")
                    .replace("{{msg2}}", "successfully created.")
                    .replace("{{msg3}}", "Please make your payment soon to confirm it.");
        }

        helper.setText(htmlContent, true);
        helper.addInline("image", image);

        mailSender.send(message);
    }
}
