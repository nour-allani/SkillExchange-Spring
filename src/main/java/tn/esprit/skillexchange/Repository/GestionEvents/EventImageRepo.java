package tn.esprit.skillexchange.Repository.GestionEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionEvents.EventImage;

import java.util.List;

public interface EventImageRepo extends JpaRepository<EventImage, Long> {
    List<EventImage> findByEventIdEvent(Long eventId);
    void deleteByEventIdEvent(Long eventId);
}