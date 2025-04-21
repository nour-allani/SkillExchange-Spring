package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;

import java.util.List;

@Repository

    public interface ParticipationCourseRepo extends JpaRepository<ParticipationCourses,Integer> {
    @Query("SELECT p FROM ParticipationCourses p WHERE p.course.id =:id")
    List<ParticipationCourses> getParticipationsByIdCourse(@Param("id") int id);

    @Query("SELECT COUNT(p) > 0 FROM ParticipationCourses p WHERE p.participant = :participantId AND p.course.id = :courseId")
    boolean existsParticipation(@Param("participantId") long participantId, @Param("courseId") int courseId);

}
