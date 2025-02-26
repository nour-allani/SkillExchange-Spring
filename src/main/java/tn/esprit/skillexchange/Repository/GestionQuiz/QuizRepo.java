package tn.esprit.skillexchange.Repository.GestionQuiz;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;

public interface QuizRepo extends JpaRepository<Quiz,Long> {
}
