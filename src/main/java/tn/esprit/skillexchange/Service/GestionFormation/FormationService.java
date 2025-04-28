package tn.esprit.skillexchange.Service.GestionFormation;

import tn.esprit.skillexchange.Entity.GestionFormation.Courses;

import java.util.List;
import java.util.Map;

public interface FormationService {
    public List<Courses> retrieveAllCourses();
    public Courses retrieveCourse(Long courseId);
    public Courses addCourse(Courses c);
    public void removeCourse(Long courseId);
    public Courses modifyCourse(Courses course);
    public List<Courses> getCoursesByUserId(int id) ;
    public void approoveDisapprooveCourse(long id);
    public Map<String, Long> getCoursesCountBySeason();

}
