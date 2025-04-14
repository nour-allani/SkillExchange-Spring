package tn.esprit.skillexchange.Repository.GestionEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.skillexchange.Entity.GestionEvents.EventComment;

import java.util.List;

public interface EventCommentRepo extends JpaRepository<EventComment, Long> {

    @Query("SELECT c FROM EventComment c LEFT JOIN FETCH c.parentComment WHERE c.event.idEvent = :eventId ORDER BY c.date DESC")
    List<EventComment> findByEventIdWithParent(@Param("eventId") Long eventId);
}