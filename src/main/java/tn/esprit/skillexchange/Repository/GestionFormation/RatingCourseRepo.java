package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionFormation.RatingCourse;


@Repository

public interface RatingCourseRepo extends JpaRepository<RatingCourse,Long> {
}
