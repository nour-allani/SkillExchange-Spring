package tn.esprit.skillexchange.Controller.GestionEvents;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Entity.GestionEvents.Status;
import tn.esprit.skillexchange.Service.GestionEvents.IParticipationEventsService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participationEvents")
@Slf4j
public class ParticipationEventsController {
    @Autowired
    private IParticipationEventsService participationEventsService;

    @GetMapping("/retrieve-ParticipationEvents")
    public List<ParticipationEvents> getAllParticipationEvents() {
        log.info("Retrieving all participation events");
        return participationEventsService.retrieveParticipationEvents();
    }

    @GetMapping("/retrieveParticipationEvents/{id}")
    public ParticipationEvents getParticipationEventsById(@PathVariable("id") Long id) {
        log.info("Retrieving participation event with ID: {}", id);
        return participationEventsService.retrieveParticipationEventsById(id);
    }

    @PostMapping("/add-ParticipationEvents")
    public ParticipationEvents addParticipationEvents(@RequestBody ParticipationEvents participationEvents) {
        try {
            log.info("Adding participation event: {}", participationEvents);
            return participationEventsService.addParticipationEvents(participationEvents);
        } catch (Exception e) {
            log.error("Error adding participation event: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/modify-ParticipationEvents")
    public ParticipationEvents updateParticipationEvents(@RequestBody ParticipationEvents participationEvents) {
        try {
            log.info("Updating participation event: {}", participationEvents);
            return participationEventsService.updateParticipationEvents(participationEvents);
        } catch (Exception e) {
            log.error("Error updating participation event: {}", e.getMessage(), e);
            throw e;
        }
    }

    @DeleteMapping("/removeParticipationEvents/{id}")
    public ResponseEntity<Void> deleteParticipationEvents(@PathVariable("id") Long id) {
        try {
            log.info("Deleting participation event with ID: {}", id);
            participationEventsService.removeParticipationEvents(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting participation event with ID: {}", id, e);
            throw e;
        }
    }

    @PostMapping("/participate/{eventId}/{status}")
    public ResponseEntity<ParticipationEvents> participateInEvent(
            @PathVariable Long eventId,
            @PathVariable Status status,
            Authentication authentication) {
        try {
            if (authentication == null || authentication.getName() == null) {
                log.error("No authenticated user found for event participation: eventId={}, status={}", eventId, status);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            String userEmail = authentication.getName();
            log.info("User {} participating in event {} with status {}", userEmail, eventId, status);
            ParticipationEvents participation = participationEventsService.participateInEvent(eventId, userEmail, status);
            return ResponseEntity.ok(participation);
        } catch (IllegalArgumentException e) {
            log.error("Invalid request for event {} with status {}: {}", eventId, status, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("Unexpected error participating in event {} with status {}: {}", eventId, status, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/countByEventAndStatus/{eventId}/{status}")
    public ResponseEntity<Long> countByEventAndStatus(@PathVariable Long eventId, @PathVariable Status status) {
        try {
            log.info("Fetching participation count for eventId: {} with status: {}", eventId, status);
            long count = participationEventsService.countByEventIdAndStatus(eventId, status);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            log.error("Error fetching participation count for eventId: {}, status: {}", eventId, status, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{email}/event/{eventId}")
    public ResponseEntity<ParticipationEvents> getParticipationByUserAndEvent(
            @PathVariable String email,
            @PathVariable Long eventId,
            Authentication authentication) {
        try {
            log.info("Fetching participation for user: {}, eventId: {}", email, eventId);
            if (authentication == null || !authentication.getName().equals(email)) {
                log.error("User {} is not authorized to access participation for {}",
                        authentication != null ? authentication.getName() : "null", email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            Optional<ParticipationEvents> participation = participationEventsService.findByEventIdAndUserEmail(eventId, email);
            log.info("Participation for user {} and event {}: {}", email, eventId, participation.isPresent() ? participation.get() : "none");
            return participation.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.ok(null));
        } catch (Exception e) {
            log.error("Error fetching participation for user: {}, eventId: {}", email, eventId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<ParticipationEvents>> getParticipationsByUserEmail(
            @PathVariable String email,
            Authentication authentication) {
        try {
            log.info("Fetching participations for user: {}", email);
            if (authentication == null || !authentication.getName().equals(email)) {
                log.error("User {} is not authorized to access participations for {}",
                        authentication != null ? authentication.getName() : "null", email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            List<ParticipationEvents> participations = participationEventsService.findByUserEmail(email);
            log.info("Participations found for user {}: {} entries", email, participations.size());
            return ResponseEntity.ok(participations);
        } catch (Exception e) {
            log.error("Error fetching participations for user: {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{email}/history")
    public ResponseEntity<List<Events>> getUserParticipationHistory(
            @PathVariable String email,
            Authentication authentication) {
        try {
            log.info("Fetching participation history for user: {}", email);
            if (authentication == null || !authentication.getName().equals(email)) {
                log.error("User {} is not authorized to access history for {}", authentication != null ? authentication.getName() : "null", email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
            }
            List<Events> events = participationEventsService.findEventsByUserEmail(email);
            log.info("Participation history for user {}: {} events", email, events.size());
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            log.error("Error fetching participation history for user: {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }



    @GetMapping("/user/{email}/events")
    @PreAuthorize("isAuthenticated()")
    public List<Events> getUserEventsByStatus(@PathVariable String email, @RequestParam Status status) {
        return participationEventsService.getEventsByUserAndStatus(email, status);
    }
}

@RestControllerAdvice
@Slf4j
class GlobalExceptionHandler {
    @ExceptionHandler(AuthorizationServiceException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleAuthorizationException(AuthorizationServiceException ex) {
        log.error("Authorization error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}