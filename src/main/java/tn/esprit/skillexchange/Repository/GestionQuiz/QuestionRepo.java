package tn.esprit.skillexchange.Repository.GestionQuiz;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionQuiz.Questions;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Questions,Long> {
    List<Questions> findByQuizId(Long quizId);

    List<Questions> findByQuiz(Quiz quiz);
}
