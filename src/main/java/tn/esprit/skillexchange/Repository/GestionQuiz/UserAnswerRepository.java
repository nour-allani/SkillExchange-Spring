package tn.esprit.skillexchange.Repository.GestionQuiz;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;
import tn.esprit.skillexchange.Entity.GestionQuiz.Questions;
import tn.esprit.skillexchange.Entity.GestionQuiz.UserAnswer;

import java.util.List;
import java.util.Optional;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
    List<UserAnswer> findByParticipationCourse(ParticipationCourses participationCourse);



    // OR if you prefer using the other relationship
    List<UserAnswer> findByParticipation(ParticipationCourses participation);

    Optional<UserAnswer> findByParticipationAndQuestion(ParticipationCourses participation, Questions question);
}

