package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.skillexchange.Entity.GestionFormation.UserCourseContentSelection;

import java.util.List;

public interface UserCourseContentSelectionRepository extends JpaRepository<UserCourseContentSelection, Integer> {

//    List<UserCourseContentSelection> findByUserId(Long userId);
//    UserCourseContentSelection findByUserIdAndCourseContentId(Long userId, int courseContentId);

    @Query("SELECT s FROM UserCourseContentSelection s WHERE s.user = :userId")
    List<UserCourseContentSelection> findByUserId(@Param("userId") int userId);

    @Query("SELECT s FROM UserCourseContentSelection s WHERE s.user = :userId AND s.courseContent= :courseContentId")
    UserCourseContentSelection findByUserIdAndCourseContentId(@Param("userId") int  userId, @Param("courseContentId") int courseContentId);

}
