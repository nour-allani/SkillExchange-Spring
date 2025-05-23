package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    @Query("SELECT c FROM Courses c WHERE c.category.id =:id")
    List<Courses> findCoursesByCategorieId(@Param("id") int id);

    @Query("SELECT c.category.id AS categoryId, COUNT(c) AS courseCount FROM Courses c WHERE c.state = 1 and c.approoved = 1 GROUP BY c.category.id")
    List<Object[]> getCourseCountPerCategory();

//    @Query("SELECT c.category.id AS categoryId, COUNT(c) AS courseCount FROM Course c GROUP BY c.category.id")
//    List<CategoryCourseCountProjection> getCourseCountPerCategory();

}
