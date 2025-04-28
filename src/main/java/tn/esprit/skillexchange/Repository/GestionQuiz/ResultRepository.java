package tn.esprit.skillexchange.Repository.GestionQuiz;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;
import tn.esprit.skillexchange.Entity.GestionQuiz.Result;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    boolean existsByParticipation(ParticipationCourses participation);
}