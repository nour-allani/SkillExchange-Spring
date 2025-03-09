package tn.esprit.skillexchange.Repository.GestionEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
@Repository
public interface EventRepo extends JpaRepository<Events,Long> {
}
