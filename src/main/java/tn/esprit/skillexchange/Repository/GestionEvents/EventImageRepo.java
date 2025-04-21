package tn.esprit.skillexchange.Repository.GestionEvents;

import tn.esprit.skillexchange.Entity.GestionEvents.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventImageRepo extends JpaRepository<EventImage, Long> {
}