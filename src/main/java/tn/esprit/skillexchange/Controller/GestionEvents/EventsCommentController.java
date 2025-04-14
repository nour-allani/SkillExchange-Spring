package tn.esprit.skillexchange.Controller.GestionEvents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionEvents.EventComment;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Service.GestionEvents.IEventCommentService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Ajout du CORS
@RequestMapping("/api/eventComments")
public class EventsCommentController {

    @Autowired
    private IEventCommentService eventCommentService;

    @GetMapping
    public ResponseEntity<List<EventComment>> getAllEventComments() {
        try {
            List<EventComment> comments = eventCommentService.retrieveEventComment();
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventComment> getEventCommentById(@PathVariable Long id) {
        try {
            EventComment comment = eventCommentService.retrieveEventCommentById(id);
            if (comment != null) {
                return ResponseEntity.ok(comment);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<?> getCommentsByEvent(@PathVariable Long eventId) {
        try {
            List<EventComment> comments = eventCommentService.getCommentsByEvent(eventId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching comments: " + e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<?> addEventComment(@RequestBody EventCommentRequest request) {
        try {
            EventComment comment = new EventComment();
            comment.setContent(request.getContent());
            comment.setEmail(request.getEmail());
            comment.setDate(new Date());

            // Créer une instance minimale de l'événement
            Events event = new Events();
            event.setIdEvent(request.getEventId());
            comment.setEvent(event);

            // Si c'est une réponse à un commentaire
            if (request.getParentCommentId() != null) {
                EventComment parent = new EventComment();
                parent.setIdComment(request.getParentCommentId());
                comment.setParentComment(parent);
            }

            EventComment savedComment = eventCommentService.addEventComment(comment);

            // Retourner une réponse simplifiée sans les relations circulaires
            Map<String, Object> response = new HashMap<>();
            response.put("idComment", savedComment.getIdComment());
            response.put("content", savedComment.getContent());
            response.put("email", savedComment.getEmail());
            response.put("date", savedComment.getDate());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error adding comment: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long id,
            @RequestBody EventCommentUpdateRequest request) {
        try {
            EventComment existingComment = eventCommentService.retrieveEventCommentById(id);
            if (existingComment == null) {
                return ResponseEntity.notFound().build();
            }

            // Ne mettre à jour que le contenu
            existingComment.setContent(request.getContent());

            EventComment updatedComment = eventCommentService.updateEventComment(existingComment);

            // Retourner une réponse simplifiée
            return ResponseEntity.ok(Map.of(
                    "idComment", updatedComment.getIdComment(),
                    "content", updatedComment.getContent(),
                    "email", updatedComment.getEmail(),
                    "date", updatedComment.getDate()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating comment: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventComment(@PathVariable Long id) {
        try {
            eventCommentService.removeEventComment(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

class EventCommentRequest {
    private String content;
    private String email;
    private Long eventId;
    private Long parentCommentId;

    // Getters and Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public Long getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(Long parentCommentId) { this.parentCommentId = parentCommentId; }
}

class EventCommentUpdateRequest {
    private String content;

    // Getters and Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}