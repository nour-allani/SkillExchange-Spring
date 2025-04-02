package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionFormation.EmojisCommentCourse;
@Repository

public interface EmojisCommentCourseRepo extends JpaRepository<EmojisCommentCourse,Long> {
}
