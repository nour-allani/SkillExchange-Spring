package tn.esprit.skillexchange.Service.GestionFormation;


import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;

import java.util.List;

public interface ParticipationCourseService {
    public List<ParticipationCourses> retrieveAllParticipations();
    public ParticipationCourses retrieveParticipation(Integer  participationId);
    public ParticipationCourses addParticipation(ParticipationCourses p);
    public void removeParticipation(Integer  participationId);
    public ParticipationCourses modifyParticipation(ParticipationCourses p);
    public List<ParticipationCourses> getParticipationsByIdCourse(int id) ;

    void assignQuizToParticipation(int participationId, Long quizId);
    public boolean checkParticipation(long userId, int courseId);

}
