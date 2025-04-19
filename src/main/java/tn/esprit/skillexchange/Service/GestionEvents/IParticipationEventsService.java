package tn.esprit.skillexchange.Service.GestionEvents;

import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Entity.GestionEvents.Status;

import java.util.List;

public interface IParticipationEventsService {
    List<ParticipationEvents> retrieveParticipationEvents();
    ParticipationEvents addParticipationEvents(ParticipationEvents participationEvents);
    ParticipationEvents updateParticipationEvents(ParticipationEvents participationEvents);
    ParticipationEvents retrieveParticipationEventsById(Long id);
    void removeParticipationEvents(Long id);
    ParticipationEvents participateInEvent(Long eventId, Status status);
    List<ParticipationEvents> findByUserEmail(String userEmail);
}