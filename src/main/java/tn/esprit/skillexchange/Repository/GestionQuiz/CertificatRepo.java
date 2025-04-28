package tn.esprit.skillexchange.Repository.GestionQuiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;
import tn.esprit.skillexchange.Entity.GestionQuiz.Certificat;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.Optional;

public interface CertificatRepo extends JpaRepository<Certificat,Long> {
    Optional<Certificat> findByQuizAndSignedBy(Quiz quiz, User user);

    @Query("SELECT c FROM Certificat c WHERE c.participation = :participation AND c.quiz = :quiz")
    Certificat findByParticipationAndQuiz(
            @Param("participation") ParticipationCourses participation,
            @Param("quiz") Quiz quiz);
}
