package tn.esprit.skillexchange.Controller.GestionQuizz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;
import tn.esprit.skillexchange.Entity.GestionQuiz.Questions;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import tn.esprit.skillexchange.Entity.GestionQuiz.Result;
import tn.esprit.skillexchange.Entity.GestionQuiz.UserAnswer;
import tn.esprit.skillexchange.Repository.GestionFormation.ParticipationCourseRepo;
import tn.esprit.skillexchange.Repository.GestionQuiz.QuestionRepo;
import tn.esprit.skillexchange.Repository.GestionQuiz.UserAnswerRepository;
import tn.esprit.skillexchange.Service.GestionFormation.FormationService;
import tn.esprit.skillexchange.Service.Gestionquizz.QuizService;
import tn.esprit.skillexchange.Service.Gestionquizz.ResultService;
import tn.esprit.skillexchange.Service.Gestionquizz.UserAnswerService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;
    @Autowired
    private FormationService formationService;
    @Autowired
    private ParticipationCourseRepo participationCourseRepository;


    @Autowired
    private UserAnswerRepository userAnswerRepository;
    @Autowired
    private QuestionRepo questionRepository;
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
            @RequestBody List<UserAnswer> userAnswers) {

        // 1. Validate participation exists
        ParticipationCourses participation = participationCourseRepository.findById(participationCourseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid participation ID"));

        // 2. Get the quiz with eager fetching
        Quiz quiz = quizService.getQuizById(quizId);

        // 3. Process each answer with duplicate prevention
        userAnswers.forEach(answer -> {
            // Validate question exists and belongs to quiz
            Questions question = questionRepository.findById(answer.getQuestion().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Question not found"));

            if(!question.getQuiz().getId().equals(quizId)) {
                throw new IllegalArgumentException("Question doesn't belong to quiz ID: " + quizId);
            }

            // Case-insensitive correctness check
            boolean isCorrect = answer.getUserAnswer().equalsIgnoreCase(question.getReponse());

            // Check for existing answer
            Optional<UserAnswer> existingAnswer = userAnswerRepository.findByParticipationAndQuestion(
                    participation,
                    question
            );

            // Update or create new answer
            if(existingAnswer.isPresent()) {
                UserAnswer existing = existingAnswer.get();
                existing.setUserAnswer(answer.getUserAnswer());
                existing.setIsCorrect(isCorrect);
                userAnswerRepository.save(existing);
            } else {
                UserAnswer newAnswer = new UserAnswer();
                newAnswer.setParticipation(participation);
                newAnswer.setQuestion(question);
                newAnswer.setUserAnswer(answer.getUserAnswer());
                newAnswer.setIsCorrect(isCorrect);
                userAnswerRepository.save(newAnswer);
            }
        });

        // 4. Calculate results considering ALL quiz questions
        return resultService.calculateResult(participationCourseId);
    }

    @PostMapping("/{quizId}/course/{courseid}")
    public Courses affectCourse(@PathVariable Long quizId, @PathVariable Long courseid) {
        return quizService.assignQuizCourse(quizId,courseid);
    }
    @GetMapping("/course/{courseid}")
    public Quiz getquizbycourse ( @PathVariable Long courseid) {
        return quizService.getquizbycourseid(courseid);

    }
}
