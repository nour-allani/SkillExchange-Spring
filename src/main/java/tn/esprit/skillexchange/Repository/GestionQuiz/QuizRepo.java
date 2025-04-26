package tn.esprit.skillexchange.Repository.GestionQuiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;

import java.util.Optional;

public interface QuizRepo extends JpaRepository<Quiz, Long> {

    @Query("SELECT c.quiz FROM Courses c WHERE c.id = :courseId")
    Optional<Quiz> findByCourseId(@Param("courseId") Long courseId);
}