package tn.esprit.skillexchange.Service.GestionFormation;


import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;

import java.util.List;

public interface ParticipationCourseService {
    public List<ParticipationCourses> retrieveAllParticipations();
    public ParticipationCourses retrieveParticipation(Long participationId);
    public ParticipationCourses addParticipation(ParticipationCourses p);
    public void removeParticipation(Long participationId);
    public ParticipationCourses modifyParticipation(ParticipationCourses p);
}
