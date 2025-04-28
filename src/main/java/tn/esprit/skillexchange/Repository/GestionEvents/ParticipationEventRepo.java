package tn.esprit.skillexchange.Repository.GestionEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Entity.GestionEvents.Status;

import java.util.List;
import java.util.Optional;

public interface ParticipationEventRepo extends JpaRepository<ParticipationEvents, Long> {
    Optional<ParticipationEvents> findByEventIdEventAndUserEmail(Long eventId, String userEmail);
    List<ParticipationEvents> findByUserEmail(String userEmail);
    long countByEventIdEventAndStatus(Long eventId, Status status);
    List<ParticipationEvents> findByUserEmailAndStatus(String email, Status status);
}