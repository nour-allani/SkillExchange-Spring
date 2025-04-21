package tn.esprit.skillexchange.Controller.GestionQuizz;

import org.springframework.http.ResponseEntity;
import tn.esprit.skillexchange.Entity.GestionQuiz.DTO.QuestionRequest;
import tn.esprit.skillexchange.Entity.GestionQuiz.Questions;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import tn.esprit.skillexchange.Service.Gestionquizz.IQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Service.Gestionquizz.QuizService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {

    @Autowired
    private IQuestionsService questionsService;
    @Autowired
    private QuizService quizService;

    @PostMapping
    public ResponseEntity<Questions> createQuestion(@RequestBody QuestionRequest questionRequest) {
        Long quizId = questionRequest.getQuizId();
        Quiz quiz = quizService.getQuizById(quizId);

        if (quiz != null) {
            Questions question = questionRequest.getQuestion();
            question.setQuiz(quiz);
            Questions savedQuestion = questionsService.saveQuestion(question);
            return ResponseEntity.ok(savedQuestion);
        }

        return ResponseEntity.badRequest().build();  // In case quizId is not found
    }

    @GetMapping
    public List<Questions> getAllQuestions() {
        return questionsService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public Optional<Questions> getQuestionById(@PathVariable Long id) {
        return questionsService.getQuestionById(id);
    }

    @PutMapping("/{id}")
    public Questions updateQuestion(@PathVariable Long id, @RequestBody Questions questionDetails) {
        Optional<Questions> question = questionsService.getQuestionById(id);
        if (question.isPresent()) {
            Questions existingQuestion = question.get();
            existingQuestion.setQuestion(questionDetails.getQuestion());
            existingQuestion.setReponse(questionDetails.getReponse());
            existingQuestion.setOption1(questionDetails.getOption1());
            existingQuestion.setOption2(questionDetails.getOption2());
            existingQuestion.setOption3(questionDetails.getOption3());
            existingQuestion.setOption4(questionDetails.getOption4());
            return questionsService.saveQuestion(existingQuestion);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionsService.deleteQuestion(id);
        return "Question deleted successfully";
    }
}
