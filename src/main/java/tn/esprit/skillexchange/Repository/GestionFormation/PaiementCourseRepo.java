package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionFormation.PaiementCoures;

@Repository

public interface PaiementCourseRepo extends JpaRepository<PaiementCoures,Integer> {


    @Query("SELECT COUNT(p) > 0 FROM PaiementCoures p WHERE p.participant = :participantId AND p.course.id = :courseId")
    boolean existsPaiement(@Param("participantId") long participantId, @Param("courseId") int courseId);

}
