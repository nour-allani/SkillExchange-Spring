package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionFormation.CourseContent;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;

import java.util.List;

@Repository
public interface CourseContentRepo extends JpaRepository<CourseContent,Integer> {

    @Query("SELECT c FROM CourseContent c WHERE c.course.id =:id ORDER BY c.order_affichage")
    List<CourseContent> getCoursesByUserId(@Param("id") int id);

}
