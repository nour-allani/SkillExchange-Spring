package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionFormation.CourseComment;

public interface CourseCommentRepo extends JpaRepository<CourseComment,Long> {
}
