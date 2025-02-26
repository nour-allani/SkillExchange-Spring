package tn.esprit.skillexchange.Repository.GestionEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionEvents.EventImage;

public interface EventImageRepo extends JpaRepository<EventImage,Long> {
}
