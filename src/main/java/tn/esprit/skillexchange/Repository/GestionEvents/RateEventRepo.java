package tn.esprit.skillexchange.Repository.GestionEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionEvents.RateEvent;

public interface RateEventRepo extends JpaRepository<RateEvent,Long> {
}
