package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionFormation.RatingCourse;

import java.util.List;


@Repository

public interface RatingCourseRepo extends JpaRepository<RatingCourse,Long> {
    // Find all ratings for a specific course
    List<RatingCourse> findByCourseId(int courseId);
    // Calculate average rating for a course
    @Query("SELECT AVG(r.rating) FROM RatingCourse r WHERE r.course.id = :courseId")
    Double findAverageRatingByCourseId(@Param("courseId") int courseId);

    // Count ratings for a course
    @Query("SELECT COUNT(r) FROM RatingCourse r WHERE r.course.id = :courseId")
    Long countByCourseId(@Param("courseId") int courseId);
}
