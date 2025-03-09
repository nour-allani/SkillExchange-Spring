package tn.esprit.skillexchange.Service.GestionEvents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Repository.GestionEvents.ParticipationEventRepo;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParticipationEventServiceImpl implements IParticipationEventsService {
    @Autowired
    ParticipationEventRepo participationEventRepo ;

    @Override
    public List<ParticipationEvents> retrieveParticipationEvents() {
        return  participationEventRepo.findAll();
    }

    @Override
    public ParticipationEvents addParticipationEvents(ParticipationEvents participationEvents) {
        return participationEventRepo.save(participationEvents);
    }

    @Override
    public ParticipationEvents updateParticipationEvents(ParticipationEvents participationEvents) {
        return participationEventRepo.save(participationEvents);
    }

    @Override
    public ParticipationEvents retrieveParticipationEventsById(Long id) {
        return participationEventRepo.findById(id).get();
    }

    @Override
    public void removeParticipationEvents(Long id) {
        participationEventRepo.deleteById(id);

    }
}
