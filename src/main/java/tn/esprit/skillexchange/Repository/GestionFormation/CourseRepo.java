package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Courses,Long> {
    @Query("SELECT c FROM Courses c WHERE c.author.id =:id")
    List<Courses> getCoursesByUserId(@Param("id") int id);
}
