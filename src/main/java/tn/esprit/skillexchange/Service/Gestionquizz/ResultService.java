package tn.esprit.skillexchange.Service.Gestionquizz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;
import tn.esprit.skillexchange.Entity.GestionQuiz.Certificat;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import tn.esprit.skillexchange.Entity.GestionQuiz.Result;
import tn.esprit.skillexchange.Entity.GestionQuiz.UserAnswer;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionFormation.ParticipationCourseRepo;
import tn.esprit.skillexchange.Repository.GestionQuiz.CertificatRepo;
import tn.esprit.skillexchange.Repository.GestionQuiz.ResultRepository;
import tn.esprit.skillexchange.Repository.GestionQuiz.UserAnswerRepository;
import tn.esprit.skillexchange.Entity.Mailing.EmailRequest;
import tn.esprit.skillexchange.Service.Mailing.GmailService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class ResultService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ParticipationCourseRepo participationCourseRepository;

    @Autowired
    private CertificatRepo certificatRepository;

    @Autowired
    private GmailService gmailService; // Inject GmailService for sending emails
    LocalDate localDate = LocalDate.now();
    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    public Result calculateResult(long participationCourseId) {
        // Fetching the participation course using the ID
        ParticipationCourses participation = participationCourseRepository.findById(participationCourseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid participation course ID"));

        // Getting the associated quiz and user
        Quiz quiz = participation.getQuiz();
        User user = participation.getUser();

        // Fetching the user's answers for the quiz
        List<UserAnswer> userAnswers = userAnswerRepository.findByParticipationCourse(participation);

        // Calculating the number of correct answers and score
        int correctAnswers = (int) userAnswers.stream().filter(UserAnswer::isCorrect).count();
        int totalQuestions = userAnswers.size();
        int score = (int) (((double) correctAnswers / totalQuestions) * 100);

        // Creating a new result object
        Result result = new Result();
        result.setParticipationCourse(participation);
        result.setScore(score);
        result.setCorrectAnswers(correctAnswers);
        result.setTotalQuestions(totalQuestions);

        // If the score is 70% or higher, generate a certificate
        if (score >= 70) {
            Certificat cert = new Certificat();
            cert.setDateCreation(date);
            cert.setQuiz(quiz);
            cert.setSignedBy(user); // Customize this field
            cert.setImage(generateCertificatImage(user.getName(), quiz.getTitle(), score)); // Generate Base64 image string

            certificatRepository.save(cert);
            result.setCertificat(cert);

            // Create an email request to send the certificate
            String emailText = "Congratulations! You have successfully completed the quiz \"" + quiz.getTitle() + "\" with a score of " + score + "%.\n\n" +
                    "Attached is your certificate of completion.";
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(user.getEmail());
            emailRequest.setSubject("Your Certificate of Completion");
            emailRequest.setText(emailText);

            // Send the email with the certificate details
            gmailService.sendSimpleEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
        }

        // Save the result object to the repository
        return resultRepository.save(result);
    }

    // Helper method to generate the certificate image and return it as a Base64 string
    private String generateCertificatImage(String fullName, String quizTitle, int score) {
        // Image dimensions
        int width = 600;
        int height = 400;

        // Create an empty image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color and font
        g2d.setColor(new Color(255, 255, 255)); // White background
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(new Color(0, 0, 0)); // Black text color
        g2d.setFont(new Font("Arial", Font.BOLD, 24));

        // Draw the certificate title
        g2d.drawString("Certificate of Completion", 150, 50);

        // Draw the quiz title
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString("Quiz: " + quizTitle, 150, 100);

        // Draw the user's name
        g2d.setFont(new Font("Arial", Font.ITALIC, 20));
        g2d.drawString("Awarded to: " + fullName, 150, 150);

        // Draw the score
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        g2d.drawString("Score: " + score + "%", 150, 200);

        // Convert image to Base64
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate certificate image", e);
        }
    }
}
