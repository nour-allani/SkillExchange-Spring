package tn.esprit.skillexchange.Service.Gestionquizz;

import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import java.util.List;

public interface QuizService {
    Quiz createQuiz(Quiz quiz);
    Quiz getQuizById(Long id);
    List<Quiz> getAllQuizzes();
    Quiz updateQuiz(Long id, Quiz quiz);
    void deleteQuiz(Long id);
}
