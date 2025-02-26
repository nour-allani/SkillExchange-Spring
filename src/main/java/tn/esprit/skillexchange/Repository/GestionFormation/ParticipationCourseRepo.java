package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;

public interface ParticipationCourseRepo extends JpaRepository<ParticipationCourses,Long> {
}
