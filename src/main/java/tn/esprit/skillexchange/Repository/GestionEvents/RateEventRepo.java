package tn.esprit.skillexchange.Repository.GestionEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionEvents.RateEvent;
@Repository
public interface RateEventRepo extends JpaRepository<RateEvent,Long> {
}