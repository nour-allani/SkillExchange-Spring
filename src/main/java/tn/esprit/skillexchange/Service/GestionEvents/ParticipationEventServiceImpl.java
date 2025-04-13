package tn.esprit.skillexchange.Service.GestionEvents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Entity.GestionEvents.Status;
import tn.esprit.skillexchange.Repository.GestionEvents.EventRepo;
import tn.esprit.skillexchange.Repository.GestionEvents.ParticipationEventRepo;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParticipationEventServiceImpl implements IParticipationEventsService {
    @Autowired
    ParticipationEventRepo participationEventRepo ;
    @Autowired
    private EventRepo eventsRepository;

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

    @Override
    public ParticipationEvents participateInEvent(Long eventId, Status status) {
        // Vérifier si l'événement existe
        Optional<Events> eventOpt = eventsRepository.findById(eventId);
        if (eventOpt.isEmpty()) {
            throw new RuntimeException("Événement non trouvé avec l'ID : " + eventId);
        }

        Events event = eventOpt.get();

        // Créer une nouvelle participation
        ParticipationEvents participation = new ParticipationEvents();
        participation.setEvent(event);
        participation.setStatus(status);

        return participationEventRepo.save(participation);
    }
}
