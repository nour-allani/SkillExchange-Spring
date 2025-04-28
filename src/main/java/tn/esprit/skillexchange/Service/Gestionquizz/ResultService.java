package tn.esprit.skillexchange.Service.Gestionquizz;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;
import tn.esprit.skillexchange.Entity.GestionQuiz.*;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import tn.esprit.skillexchange.Entity.Mailing.EmailRequest;
import tn.esprit.skillexchange.Repository.GestionFormation.ParticipationCourseRepo;
import tn.esprit.skillexchange.Repository.GestionQuiz.CertificatRepo;
import tn.esprit.skillexchange.Repository.GestionQuiz.QuestionRepo;
import tn.esprit.skillexchange.Repository.GestionQuiz.ResultRepository;
import tn.esprit.skillexchange.Repository.GestionQuiz.UserAnswerRepository;

import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.Mailing.GmailService;

import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class ResultService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ParticipationCourseRepo participationCourseRepository;

    @Autowired
    private CertificatRepo certificatRepository;

    @Autowired
    private QuestionRepo questionRepository;
    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);
    @Autowired
    private GmailService gmailService; // Inject GmailService for sending emails
    @Value("${app.base-url}")
    private String baseUrl;
    LocalDate localDate = LocalDate.now();
    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    public Result calculateResult(int participationCourseId) {
        // Retrieve the participation record
        ParticipationCourses participation = participationCourseRepository.findById(participationCourseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid participation ID"));

        // Get the quiz associated with the participation
        Quiz quiz = participation.getQuiz();
        List<Questions> quizQuestions = questionRepository.findByQuiz(quiz);
        int totalQuestions = quizQuestions.size();

        // Retrieve the user's answers for the quiz
        List<UserAnswer> userAnswers = userAnswerRepository.findByParticipation(participation);
        Map<Long, UserAnswer> answerMap = userAnswers.stream()
                .collect(Collectors.toMap(a -> a.getQuestion().getId(), Function.identity()));

        // Calculate the number of correct answers
        int correctAnswers = (int) quizQuestions.stream()
                .filter(question -> {
                    UserAnswer answer = answerMap.get(question.getId());
                    return answer != null && Boolean.TRUE.equals(answer.getIsCorrect());
                })
                .count();

        // Calculate the score as a percentage
        int score = totalQuestions > 0 ? (correctAnswers * 100) / totalQuestions : 0;

        // Create and set the result
        Result result = new Result();
        result.setParticipation(participation);
        result.setScore(score);
        result.setTotalQuestions(totalQuestions);
        result.setCorrectAnswers(correctAnswers);

        // If the score is >= 70, generate a certificate
        if (score >= 70) {
            Certificat cert = generateCertificate(participation, quiz, score);
            result.setCertificat(cert);
        }

        return resultRepository.save(result);
    }

    private Certificat generateCertificate(ParticipationCourses participation, Quiz quiz, int score) {
        // Check if certificate already exists
        Certificat existingCert = certificatRepository.findByParticipationAndQuiz(participation, quiz);
        if (existingCert != null) {
            return existingCert;
        }

        // Get the user
        User user = userRepository.findById((long) participation.getParticipant())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Certificat cert = new Certificat();
        cert.setDateCreation(new Date());
        cert.setQuiz(quiz);
        cert.setParticipation(participation);
        cert.setTitle(quiz.getTitle());


        try {
            // Save once to generate ID
            cert = certificatRepository.save(cert);

            String verificationUrl = baseUrl + "/skillExchange/api/certificats/" + cert.getId() + "/view";
            cert.setVerificationUrl(verificationUrl);
            cert.setQrCode(generateQRCode(verificationUrl));

            // Generate HTML + PDF
            String htmlContent = generateCertificateContent(user, quiz.getTitle(), score, verificationUrl);
            cert.setHtmlContent(htmlContent);
            cert.setPdfContent(generatePdfCertificate(htmlContent));

            // Save updated certificate
            certificatRepository.save(cert);

            // Send by mail
            sendCertificateEmail(user, cert, score, quiz.getTitle());

        } catch (IOException e) {
            logger.error("PDF generation failed for participation ID: " + participation.getIdp(), e);
            throw new RuntimeException("PDF generation failed", e);
        } catch (Exception e) {
            logger.error("Error in certificate generation", e);
            throw new RuntimeException("Certificate generation failed", e);
        }

        return cert;
    }



    private String generatePdfCertificate(String htmlContent) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

        try {
            // Add base URL for external resources
            renderer.getSharedContext().setBaseURL("classpath:/static/");
            renderer.setDocumentFromString(htmlContent);

            renderer.layout();
            renderer.createPDF(outputStream);
            renderer.finishPDF();
        } catch (com.itextpdf.text.DocumentException e) {
            throw new IOException("PDF generation failed", e);
        }

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public String generateQRCode(String data) {
        try {
            // Validate input
            if (data == null || data.trim().isEmpty()) {
                throw new IllegalArgumentException("Data for QR code cannot be null or empty");
            }

            // Ensure we're using the configured base URL
            String fullUrl;
            if (data.startsWith("http://") || data.startsWith("https://")) {
                fullUrl = data; // Use as-is if already a full URL
            } else {
                // Clean the path (remove leading/trailing slashes)
                String cleanPath = data.replaceAll("^/+|/+$", "");
                fullUrl = baseUrl + "/" + cleanPath;

                // Ensure it uses HTTPS in production
                if (!fullUrl.startsWith("http")) {
                    fullUrl = "https://" + fullUrl;
                }
            }

            // Generate QR code with better error correction
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // Medium error correction
            hints.put(EncodeHintType.MARGIN, 1); // Small margin

            BitMatrix bitMatrix = qrCodeWriter.encode(
                    fullUrl,
                    BarcodeFormat.QR_CODE,
                    300, 300, // Increased size for better scanning
                    hints
            );

            // Convert to image with better quality
            BufferedImage bufferedImage = new BufferedImage(
                    300, 300,
                    BufferedImage.TYPE_INT_RGB
            );

            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, 300, 300);
            graphics.setColor(Color.BLACK);

            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    if (bitMatrix.get(x, y)) {
                        graphics.fillRect(x, y, 1, 1);
                    }
                }
            }

            // Convert to PNG with compression
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

            return "data:image/png;base64," +
                    Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());

        } catch (WriterException e) {
            logger.error("Failed to encode QR code for data: " + data, e);
            throw new RuntimeException("QR code generation failed: " + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("Failed to convert QR code to image for data: " + data, e);
            throw new RuntimeException("Image conversion failed: " + e.getMessage(), e);
        }
    }
    private String generateCertificateContent(User user, String courseName, int score, String verificationUrl) throws IOException {
        // Load the certificate template HTML
        ClassPathResource resource = new ClassPathResource("templates/email/certificate-template.html");
        String html = new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);

        // Replace placeholders with actual data
        return html.replace("{{courseName}}", courseName)
                .replace("{{userName}}", user.getName())
                .replace("{{score}}", String.valueOf(score))
                .replace("{{signature}}", Optional.ofNullable(user.getSignature()).orElse("")) // Signature (base64 image)
                .replace("{{signedBy}}", "SkillExchange") // Signature text
                .replace("{{qrCode}}", generateQRCode(verificationUrl)) // QR Code for verification
                .replace("{{appLogo}}", "data:image/png;base64," + getAppLogoBase64()); // Optional: App logo in base64
    }
    private String getAppLogoBase64() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/uploads/img.png");
        byte[] imageBytes = Files.readAllBytes(resource.getFile().toPath());
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Update sendCertificateEmail
    private void sendCertificateEmail(User user, Certificat cert, int score, String courseName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(user.getEmail());
            helper.setSubject("Quiz Results - " + courseName);

            String emailBody = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Quiz Results</title>\n" +
                    "    <style>\n" +
                    "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }\n" +
                    "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }\n" +
                    "        .header { background-color: #f28c38; color: white; padding: 15px; text-align: center; border-radius: 5px 5px 0 0; }\n" +
                    "        .content { background-color: #f9f9f9; padding: 20px; border: 1px solid #ddd; border-top: none; border-radius: 0 0 5px 5px; }\n" +
                    "        .button { display: inline-block; padding: 10px 20px; background-color: #f28c38; color: white; text-decoration: none; border-radius: 5px; margin-top: 10px; }\n" +
                    "        .footer { text-align: center; margin-top: 20px; font-size: 12px; color: #777; }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div class=\"container\">\n" +
                    "    <div class=\"header\">\n" +
                    "        <h2>Quiz Results</h2>\n" +
                    "    </div>\n" +
                    "    <div class=\"content\">\n" +
                    "        <p>Dear " + user.getName() + ",</p>\n" +
                    "        <p>Thank you for completing the quiz for <strong>" + courseName + "</strong>!</p>\n" +
                    "        <p><strong>Your Score:</strong> " + score + "%</p>\n" +
                    (score >= 70
                            ? "<p>🎉 Congratulations! You have passed the quiz. Your certificate is attached to this email.</p>\n"
                            : "<p>Unfortunately, you did not achieve the passing score. Keep practicing and success will follow!</p>\n") +
                    "        <p>Keep learning and exploring new skills on our platform!</p>\n" +
                    "        <a href=\"http://localhost:4200/courses\" class=\"button\">Explore More Courses</a>\n" +
                    "        <p>Best regards,<br>The SkillExchange Team</p>\n" +
                    "    </div>\n" +
                    "    <div class=\"footer\">\n" +
                    "        <p>© 2025 SkillExchange. All rights reserved.</p>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";

            helper.setText(emailBody, true);

            if (score >= 70) {
                // Attach the PDF certificate
                byte[] pdfBytes = Base64.getDecoder().decode(cert.getPdfContent());
                helper.addAttachment("certificate.pdf", new ByteArrayResource(pdfBytes));
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}