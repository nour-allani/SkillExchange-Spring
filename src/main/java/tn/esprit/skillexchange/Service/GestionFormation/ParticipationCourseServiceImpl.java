package tn.esprit.skillexchange.Service.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionFormation.CourseRepo;
import tn.esprit.skillexchange.Repository.GestionFormation.ParticipationCourseRepo;
import tn.esprit.skillexchange.Repository.GestionQuiz.QuizRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ParticipationCourseServiceImpl implements ParticipationCourseService{

    @Autowired
    ParticipationCourseRepo participationCourseRepo ;
    @Autowired
    QuizRepo quizRepo ;
    @Autowired
    UserRepo userRepo ;
    @Autowired
    CourseRepo courseRepo ;
    @Override
    public List<ParticipationCourses> retrieveAllParticipations() {
        return participationCourseRepo.findAll() ;
    }

    @Override
    public ParticipationCourses retrieveParticipation(Integer  participationId) {
        return participationCourseRepo.findById(participationId).get();
    }

    @Override
    public ParticipationCourses addParticipation(ParticipationCourses p) {
        p.setDate_participation(java.sql.Date.valueOf(LocalDate.now()));

        return participationCourseRepo.save(p);
    }


    @Override
    public void removeParticipation(Integer  participationId) {
        participationCourseRepo.deleteById(participationId);
    }

    @Override
    public ParticipationCourses modifyParticipation(ParticipationCourses p) {
        return participationCourseRepo.save(p);
    }

    @Override
    public List<ParticipationCourses> getParticipationsByIdCourse(int id) {
        return participationCourseRepo.getParticipationsByIdCourse(id) ;
    }
    @Override
    public void assignQuizToParticipation(int participationId, Long quizId) {
        ParticipationCourses participation = participationCourseRepo.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation not found"));

        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        participation.setQuiz(quiz);
        participationCourseRepo.save(participation);
    }

    @Override
    public boolean checkParticipation(long userId, int courseId) {
        return participationCourseRepo.existsParticipation(userId, courseId);
    }

}
