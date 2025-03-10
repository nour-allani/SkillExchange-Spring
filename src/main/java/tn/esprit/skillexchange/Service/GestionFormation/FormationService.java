package tn.esprit.skillexchange.Service.GestionFormation;

import tn.esprit.skillexchange.Entity.GestionFormation.Courses;

import java.util.List;

public interface FormationService {
    public List<Courses> retrieveAllCourses();
    public Courses retrieveCourse(Long courseId);
    public Courses addCourse(Courses c);
    public void removeCourse(Long courseId);
    public Courses modifyCourse(Courses course);
}
