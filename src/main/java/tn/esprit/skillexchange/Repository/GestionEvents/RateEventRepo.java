package tn.esprit.skillexchange.Repository.GestionEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionEvents.RateEvent;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateEventRepo extends JpaRepository<RateEvent, Long> {
    List<RateEvent> findByEventIdEvent(Long eventId);
    Optional<RateEvent> findByUserIdAndEventIdEvent(Long userId, Long eventId);}
