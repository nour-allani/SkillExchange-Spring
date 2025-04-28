package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Courses,Long> {
    @Query("SELECT c FROM Courses c WHERE c.author.id =:id")
    List<Courses> getCoursesByUserId(@Param("id") int id);

//    @Query("SELECT CASE " +
//            "WHEN MONTH(c.date_ajout) IN (3, 4, 5) THEN 'Printemps' " +
//            "WHEN MONTH(c.date_ajout) IN (6, 7, 8) THEN 'Été' " +
//            "WHEN MONTH(c.date_ajout) IN (9, 10, 11) THEN 'Automne' " +
//            "ELSE 'Hiver' END AS saison, COUNT(c) " +
//            "FROM Courses c GROUP BY saison")
@Query("SELECT MONTHNAME(c.date_ajout) AS month, COUNT(c) " +
        "FROM Courses c GROUP BY MONTH(c.date_ajout), MONTHNAME(c.date_ajout) " +
        "ORDER BY MONTH(c.date_ajout)")
    List<Object[]> countCoursesBySeason();
    Optional<Courses> findByQuiz(Quiz quiz);

}
