package tn.esprit.skillexchange.Service.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import tn.esprit.skillexchange.Repository.GestionFormation.CourseRepo;
import tn.esprit.skillexchange.Repository.GestionQuiz.QuizRepo;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class FormationServiceImpl implements FormationService {

    @Autowired
    CourseRepo courseRepo;
    @Autowired
    QuizRepo quizRepo;

    @Override
    public List<Courses> retrieveAllCourses() {
        return courseRepo.findAll();
    }

    @Override
    public Courses retrieveCourse(Long courseId) {
        return courseRepo.findById(courseId).get();
    }

    @Override
    public Courses addCourse(Courses c) {
        c.setDate_ajout(java.sql.Date.valueOf(LocalDate.now()));
        return courseRepo.save(c);
    }

    @Override
    public void removeCourse(Long courseId) {
       courseRepo.deleteById(courseId);
    }

    @Override
    public Courses modifyCourse(Courses course) {
        return courseRepo.save(course);
    }

    @Override
    public List<Courses> getCoursesByUserId(int id) {
        return courseRepo.getCoursesByUserId(id) ;
    }
    public void assignQuizToCourse(Long courseId, Long quizId) {
        Courses course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        course.setQuiz(quiz);
        courseRepo.save(course);
    }

}
