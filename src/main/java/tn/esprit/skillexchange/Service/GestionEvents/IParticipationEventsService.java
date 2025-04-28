package tn.esprit.skillexchange.Service.GestionEvents;

import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Entity.GestionEvents.Status;

import java.util.List;
import java.util.Optional;

public interface IParticipationEventsService {
    List<ParticipationEvents> retrieveParticipationEvents();
    ParticipationEvents retrieveParticipationEventsById(Long id);
    ParticipationEvents addParticipationEvents(ParticipationEvents participationEvents);
    ParticipationEvents updateParticipationEvents(ParticipationEvents participationEvents);
    void removeParticipationEvents(Long id);
    ParticipationEvents participateInEvent(Long eventId, String userEmail, Status status);
    List<ParticipationEvents> findByUserEmail(String userEmail);
    long countByEventIdAndStatus(Long eventId, Status status);
    Optional<ParticipationEvents> findByEventIdAndUserEmail(Long eventId, String userEmail);

    // pour l AI
    List<Events> findEventsByUserEmail(String userEmail);
    List<Events> getEventsByUserAndStatus(String userEmail, Status status);

}