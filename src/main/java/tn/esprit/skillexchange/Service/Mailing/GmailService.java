package tn.esprit.skillexchange.Service.Mailing;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

@Service
public class GmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${frontend.base.url:http://localhost:4200}")
    private String frontendBaseUrl;

    public void sendSimpleEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        mailSender.send(message);
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String to, String resetCode) throws MessagingException, IOException {
        String htmlContent = loadEmailTemplate("templates/email/password-reset-template.html")
                .replace("{{code}}", resetCode);
        sendHtmlEmail(to, "Password Reset Request", htmlContent);
    }

    public void sendAccountVerificationEmail(String to, String verificationCode) throws MessagingException, IOException {
        String htmlContent = loadEmailTemplate("templates/email/account-verify-template.html")
                .replace("{{code}}", verificationCode);
        sendHtmlEmail(to, "Account Verification", htmlContent);
    }

    public void sendEventConfirmationEmail(String to, String userName, String eventName, String startDate, String endDate, String place, String status, Long eventId) throws MessagingException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String htmlContent = loadEmailTemplate("/templates/email/event.html")
                .replace("{{userName}}", userName != null ? userName : "User")
                .replace("{{eventName}}", eventName)
                .replace("{{startDate}}", dateFormat.format(java.util.Date.from(java.time.Instant.parse(startDate))))
                .replace("{{endDate}}", dateFormat.format(java.util.Date.from(java.time.Instant.parse(endDate))))
                .replace("{{place}}", place)
                .replace("{{status}}", status)
                .replace("{{eventUrl}}", frontendBaseUrl + "/events/" + eventId);
        sendHtmlEmail(to, "Event Participation Confirmation", htmlContent);
    }

    private String loadEmailTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }



}