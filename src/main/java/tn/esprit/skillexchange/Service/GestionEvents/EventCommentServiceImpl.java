package tn.esprit.skillexchange.Service.GestionEvents;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionEvents.EventComment;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Repository.GestionEvents.EventCommentRepo;
import tn.esprit.skillexchange.Repository.GestionEvents.EventRepo;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventCommentServiceImpl implements IEventCommentService {
    @Autowired
    EventCommentRepo eventCommentRepo;
    private final EventRepo eventRepo;

    @Override
    public List<EventComment> retrieveEventComment() {
        return eventCommentRepo.findAll();
    }

    @Override
    public List<EventComment> getCommentsByEvent(Long eventId) {
        return eventCommentRepo.findByEventIdWithParent(eventId);}

    @Override
    public EventComment addEventComment(EventComment comment) {
        try {
            // Gestion de l'événement associé
            if (comment.getEvent() != null && comment.getEvent().getIdEvent() != null) {
                Events event = eventRepo.findById(comment.getEvent().getIdEvent())
                        .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + comment.getEvent().getIdEvent()));
                comment.setEvent(event);
            }

            // Gestion du commentaire parent
            if (comment.getParentComment() != null && comment.getParentComment().getIdComment() != null) {
                EventComment parent = eventCommentRepo.findById(comment.getParentComment().getIdComment())
                        .orElseThrow(() -> new EntityNotFoundException("Parent comment not found with id: " + comment.getParentComment().getIdComment()));
                comment.setParentComment(parent);
            }

            return eventCommentRepo.save(comment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save comment: " + e.getMessage(), e);
        }
    }
    @Override
    @Transactional
    public EventComment updateEventComment(EventComment comment) {
        // Charger l'entité existante
        EventComment existingComment = eventCommentRepo.findById(comment.getIdComment())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // Ne mettre à jour que les champs modifiables
        existingComment.setContent(comment.getContent());

        return eventCommentRepo.save(existingComment);
    }

    @Override
    public EventComment retrieveEventCommentById(Long id) {
        return eventCommentRepo.findById(id).orElse(null);
    }

    @Override
    public void removeEventComment(Long id) {
        eventCommentRepo.deleteById(id);
    }
}