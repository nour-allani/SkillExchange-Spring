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

import lombok.extern.slf4j.Slf4j;
import tn.esprit.skillexchange.Entity.Mailing.EmailRequest;


import java.io.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;


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
    public void sendMentionNotification(String to, String mentionedBy, String postContent) throws MessagingException {
        String subject = "Vous avez été mentionné dans un commentaire";
        String text = "Bonjour,\n\nVous avez été mentionné par " + mentionedBy +
                " dans un commentaire :\n\n\"" + postContent + "\"\n\nConnectez-vous pour répondre.";

        sendSimpleEmail(to, subject, text);
    }
    public void sendPostApprovalHtmlEmail(String to, String title) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("✅ Your post was approved");

        String html = loadHtmlTemplateWithPost("templates/email/approve-post.html", title);
        helper.setText(html, true);
        helper.addInline("logo25", new ClassPathResource("static/logo25.jpg").getFile());

        mailSender.send(message);
    }
    private String loadHtmlTemplateWithPost(String path, String title) throws IOException {
        InputStream input = new ClassPathResource(path).getInputStream();
        String html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        return html.replace("{{title}}", title);
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

        helper.setFrom(fromEmail); 
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); 

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




    private byte[] generateQRCode(String text, int width, int height) throws WriterException, IOException {
        log.info("Generating QR code with text: {}, width: {}, height: {}", text, width, height);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] qrCodeImage = pngOutputStream.toByteArray();
        log.info("QR code generated, size: {} bytes", qrCodeImage.length);
        return qrCodeImage;
    }


        mailSender.send(message);
    }

}


    public void sendMail(EmailRequest emailRequest) throws MessagingException {
        if (emailRequest.getText().contains("<html>")) {
            sendHtmlEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
        } else {
            sendSimpleEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
        }
    }

    ///////////////////////////////Gestion Post /////////////////////////////////////////////////
 public void sendMentionNotification(String to, String mentionedBy, String postContent) throws MessagingException {
        String subject = "Vous avez été mentionné dans un commentaire";
        String text = "Bonjour,\n\nVous avez été mentionné par " + mentionedBy +
                " dans un commentaire :\n\n\"" + postContent + "\"\n\nConnectez-vous pour répondre.";

        sendSimpleEmail(to, subject, text);
    }
 public void sendPostApprovalHtmlEmail(String to, String title) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("✅ Your post was approved");

        String html = loadHtmlTemplateWithPost("templates/email/approve-post.html", title);
        helper.setText(html, true);
        helper.addInline("logo25", new ClassPathResource("static/logo25.jpg").getFile());

        mailSender.send(message);
    }
  private String loadHtmlTemplateWithPost(String path, String title) throws IOException {
        InputStream input = new ClassPathResource(path).getInputStream();
        String html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        return html.replace("{{title}}", title);
    }
 public void sendPostRejectionHtmlEmail(String to, String title) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("❌ Your post was rejected");

        String html = loadHtmlTemplateWithPost("templates/email/reject-post.html", title);
        helper.setText(html, true);
        helper.addInline("logo25", new ClassPathResource("static/logo25.jpg").getFile());

        mailSender.send(message);
    }
    ///////////////////////////////Gestion Post /////////////////////////////////////////////////
    public void sendMentionNotification(String to, String mentionedBy, String postContent) throws MessagingException {
        String subject = "Vous avez été mentionné dans un commentaire";
        String text = "Bonjour,\n\nVous avez été mentionné par " + mentionedBy +
                " dans un commentaire :\n\n\"" + postContent + "\"\n\nConnectez-vous pour répondre.";

        sendSimpleEmail(to, subject, text);
    }
    public void sendPostApprovalHtmlEmail(String to, String title) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("✅ Your post was approved");

        String html = loadHtmlTemplateWithPost("templates/email/approve-post.html", title);
        helper.setText(html, true);
        helper.addInline("logo25", new ClassPathResource("static/logo25.jpg").getFile());

        mailSender.send(message);
    }
    private String loadHtmlTemplateWithPost(String path, String title) throws IOException {
        InputStream input = new ClassPathResource(path).getInputStream();
        String html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        return html.replace("{{title}}", title);
    }
    public void sendPostRejectionHtmlEmail(String to, String title) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("❌ Your post was rejected");

        String html = loadHtmlTemplateWithPost("templates/email/reject-post.html", title);
        helper.setText(html, true);
        helper.addInline("logo25", new ClassPathResource("static/logo25.jpg").getFile());

        mailSender.send(message);
    }
}





