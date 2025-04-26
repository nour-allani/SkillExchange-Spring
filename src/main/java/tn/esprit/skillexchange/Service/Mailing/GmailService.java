package tn.esprit.skillexchange.Service.Mailing;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class GmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

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

    private String loadEmailTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }
    ///////////////////////////////Gestion Produit /////////////////////////////////////////////////
    public void sendEmailWithAttachment(String to, String subject, String text, byte[] pdfContent, String filename)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        helper.addAttachment(filename, new ByteArrayResource(pdfContent));

        mailSender.send(message);
    }
    public void sendInvoiceHtmlEmail(String to, String subject, String htmlContent, byte[] pdfBytes, String filename)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail); // assure-toi que fromEmail est bien injecté
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // ✅ true pour HTML

        helper.addAttachment(filename, new ByteArrayResource(pdfBytes));

        mailSender.send(message);
        System.out.println("📧 Invoice HTML email sent to: " + to);
    }
    public void sendProductApprovalHtmlEmail(String to, String productName) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("✅ Your product was approved");

        String html = loadHtmlTemplateWithProduct("templates/email/product-approve-template.html", productName);
        helper.setText(html, true);
        helper.addInline("logo25", new ClassPathResource("static/logo25.png").getFile());

        mailSender.send(message);
    }
    public void sendProductRejectionHtmlEmail(String to, String productName) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("❌ Your product was rejected");

        String html = loadHtmlTemplateWithProduct("templates/email/product-reject-template.html", productName);
        helper.setText(html, true);
        helper.addInline("logo25", new ClassPathResource("static/logo25.png").getFile());

        mailSender.send(message);
    }
    private String loadHtmlTemplateWithProduct(String path, String productName) throws IOException {
        InputStream input = new ClassPathResource(path).getInputStream();
        String html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        return html.replace("{{productName}}", productName);
    }




}
