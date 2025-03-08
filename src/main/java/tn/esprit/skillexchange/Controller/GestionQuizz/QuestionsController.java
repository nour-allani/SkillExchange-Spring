package tn.esprit.skillexchange.Controller.GestionQuizz;

import tn.esprit.skillexchange.Entity.GestionQuiz.Questions;
import tn.esprit.skillexchange.Service.Gestionquizz.IQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {

    @Autowired
    private IQuestionsService questionsService;

    @PostMapping
    public Questions createQuestion(@RequestBody Questions question) {
        return questionsService.saveQuestion(question);
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
