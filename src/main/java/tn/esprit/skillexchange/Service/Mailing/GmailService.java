package tn.esprit.skillexchange.Service.Mailing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
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

    public void sendEventConfirmationEmail(String to, String userName, String eventName, String startDate, String endDate, String place, String status, Long eventId, Long participationId) throws MessagingException, IOException, WriterException {
        log.info("Preparing event confirmation email for: to={}, userName={}, eventName={}, participationId={}", to, userName, eventName, participationId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedStartDate = "Unknown";
        String formattedEndDate = "Unknown";
        try {
            // Parse dates in "yyyy-MM-dd HH:mm:ss.S" format
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            Date start = inputFormat.parse(startDate);
            Date end = inputFormat.parse(endDate);
            formattedStartDate = dateFormat.format(start);
            formattedEndDate = dateFormat.format(end);
        } catch (Exception e) {
            log.error("Error parsing dates: startDate={}, endDate={}", startDate, endDate, e);
        }
        String htmlContent;
        try {
            htmlContent = loadEmailTemplate("templates/email/event.html");
        } catch (IOException e) {
            log.error("Failed to load email template: {}", e.getMessage(), e);
            throw e;
        }
        htmlContent = htmlContent
                .replace("{{userName}}", userName != null ? userName : "User")
                .replace("{{eventName}}", eventName != null ? eventName : "Unknown Event")
                .replace("{{startDate}}", formattedStartDate)
                .replace("{{endDate}}", formattedEndDate)
                .replace("{{place}}", place != null ? place : "Unknown Location")
                .replace("{{status}}", status != null ? status : "Unknown")
                .replace("{{eventUrl}}", frontendBaseUrl + "/events/" + eventId);

        // Generate QR code with readable message
        if (to == null || userName == null || eventName == null || eventId == null || participationId == null) {
            log.error("Invalid QR code inputs: to={}, userName={}, eventName={}, eventId={}, participationId={}", to, userName, eventName, eventId, participationId);
            throw new IllegalArgumentException("Cannot generate QR code with null inputs");
        }
        String qrContent = String.format("This user %s with email %s is successfully registered for the event %s.", userName, to, eventName);
        log.info("QR code content: {}", qrContent);
        if (qrContent.trim().isEmpty()) {
            log.error("QR code content is empty");
            throw new IllegalStateException("Cannot generate QR code with empty content");
        }
        // Save QR code for testing
        testGenerateQRCode(qrContent, "qr_test.png");
        byte[] qrCodeImage = generateQRCode(qrContent, 250, 250);

        // Send email with QR code
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject("Event Participation Confirmation");
        helper.setText(htmlContent, true);
        helper.addInline("qrcode", new ByteArrayResource(qrCodeImage), "image/png");

        mailSender.send(message);
        log.info("Sent confirmation email with QR code to: {}", to);
    }

    private String loadEmailTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
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

    private void testGenerateQRCode(String content, String filePath) throws WriterException, IOException {
        log.info("Testing QR code generation with content: {}", content);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 250, 250);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", java.nio.file.Paths.get(filePath));
        log.info("QR code saved to: {}", filePath);
    }
}