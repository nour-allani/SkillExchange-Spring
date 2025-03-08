package tn.esprit.skillexchange.Repository.GestionEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionEvents.EventComment;

public interface EventCommentRepo extends JpaRepository<EventComment,Long> {
}
