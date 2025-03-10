package tn.esprit.skillexchange.Service.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;
import tn.esprit.skillexchange.Repository.GestionFormation.ParticipationCourseRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class ParticipationCourseServiceImpl implements ParticipationCourseService{

    @Autowired
    ParticipationCourseRepo participationCourseRepo ;
    @Override
    public List<ParticipationCourses> retrieveAllParticipations() {
        return participationCourseRepo.findAll() ;
    }

    @Override
    public ParticipationCourses retrieveParticipation(Long participationId) {
        return participationCourseRepo.findById(participationId).get();
    }

    @Override
    public ParticipationCourses addParticipation(ParticipationCourses p) {
        return participationCourseRepo.save(p);
    }

    @Override
    public void removeParticipation(Long participationId) {
        participationCourseRepo.deleteById(participationId);
    }

    @Override
    public ParticipationCourses modifyParticipation(ParticipationCourses p) {
        return participationCourseRepo.save(p);
    }
}
