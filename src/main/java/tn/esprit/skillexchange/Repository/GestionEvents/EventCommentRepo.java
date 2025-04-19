package tn.esprit.skillexchange.Repository.GestionEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionEvents.EventComment;
@Repository
public interface EventCommentRepo extends JpaRepository<EventComment,Long> {
}
