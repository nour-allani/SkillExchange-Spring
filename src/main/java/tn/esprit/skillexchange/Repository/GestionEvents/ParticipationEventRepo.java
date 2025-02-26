package tn.esprit.skillexchange.Repository.GestionEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;

public interface ParticipationEventRepo extends JpaRepository<ParticipationEvents,Long> {
}
