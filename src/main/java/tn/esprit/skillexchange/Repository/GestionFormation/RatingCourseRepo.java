package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionFormation.RatingCourse;

public interface RatingCourseRepo extends JpaRepository<RatingCourse,Long> {
}
