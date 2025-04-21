package tn.esprit.skillexchange.Controller.GestionQuizz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import tn.esprit.skillexchange.Entity.GestionQuiz.Result;
import tn.esprit.skillexchange.Entity.GestionQuiz.UserAnswer;
import tn.esprit.skillexchange.Service.GestionFormation.FormationService;
import tn.esprit.skillexchange.Service.Gestionquizz.QuizService;
import tn.esprit.skillexchange.Service.Gestionquizz.ResultService;
import tn.esprit.skillexchange.Service.Gestionquizz.UserAnswerService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;
    @Autowired
    private FormationService formationService;


    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private ResultService resultService;
    @PostMapping
    public Quiz createQuiz(@RequestParam("title") String title,
                           @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        String base64Image = file != null ? Base64.getEncoder().encodeToString(file.getBytes()) : null;
        return quizService.createQuiz(title, base64Image);
    }

    @PutMapping("/{id}")
    public Quiz updateQuiz(@PathVariable Long id,
                           @RequestParam("title") String title,
                           @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        String base64Image = file != null ? Base64.getEncoder().encodeToString(file.getBytes()) : null;
        return quizService.updateQuiz(id, title, base64Image);
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }


    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
    }
    // Add this new endpoint
    @PostMapping("/{id}/image")
    public Quiz uploadImage(@PathVariable Long id,
                            @RequestParam("file") MultipartFile file) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        return quizService.updateQuizImage(id, base64Image);
    }
    @PostMapping("/{quizId}/answers")
    public UserAnswer submitAnswer(@PathVariable Long quizId, @RequestBody UserAnswer userAnswer) {
        // Set the quiz id
        userAnswer.setQuiz(quizService.getQuizById(quizId));
        return userAnswerService.saveUserAnswer(userAnswer);
    }

    // New method to submit the final result for the user's participation in the quiz

    @PostMapping("/{quizId}/results")
    public Result submitResult(
            @PathVariable Long quizId,
            @RequestParam("participationCourseId") int participationCourseId,
            @RequestBody List<UserAnswer> userAnswers
    ) {
        // Save all user answers
        userAnswers.forEach(answer -> {
            answer.setQuiz(quizService.getQuizById(quizId));
            userAnswerService.saveUserAnswer(answer);
        });

        // Calculate and return result
        return resultService.calculateResult(participationCourseId);
    }
    @PostMapping("/{quizId}/course/{courseid}")
    public Courses affectCourse(@PathVariable Long quizId, @PathVariable Long courseid) {
        return quizService.assignQuizCourse(quizId,courseid);
    }
}
