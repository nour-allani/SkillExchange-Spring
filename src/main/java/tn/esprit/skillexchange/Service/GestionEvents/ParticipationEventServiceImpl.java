package tn.esprit.skillexchange.Service.GestionEvents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Entity.GestionEvents.Status;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionEvents.EventRepo;
import tn.esprit.skillexchange.Repository.GestionEvents.ParticipationEventRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.Mailing.GmailService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParticipationEventServiceImpl implements IParticipationEventsService {
    @Autowired
    private ParticipationEventRepo participationEventRepo;
    @Autowired
    private EventRepo eventsRepository;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private GmailService gmailService;

    @Override
    public List<ParticipationEvents> retrieveParticipationEvents() {
        log.info("Retrieving all participation events");
        return participationEventRepo.findAll();
    }

    @Override
    public ParticipationEvents addParticipationEvents(ParticipationEvents participationEvents) {
        log.info("Adding participation: {}", participationEvents);
        return participationEventRepo.save(participationEvents);
    }

    @Override
    public ParticipationEvents updateParticipationEvents(ParticipationEvents participationEvents) {
        log.info("Updating participation: {}", participationEvents);
        return participationEventRepo.save(participationEvents);
    }

    @Override
    public ParticipationEvents retrieveParticipationEventsById(Long id) {
        log.info("Retrieving participation by ID: {}", id);
        return participationEventRepo.findById(id).orElseThrow(() -> {
            log.error("Participation not found with ID: {}", id);
            return new RuntimeException("Participation not found");
        });
    }

    @Override
    public void removeParticipationEvents(Long id) {
        log.info("Removing participation with ID: {}", id);
        participationEventRepo.deleteById(id);
    }

    @Override
    public ParticipationEvents participateInEvent(Long eventId, Status status) {
        try {
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info("Authenticated user email: {}", userEmail);
            if (userEmail == null || userEmail.isEmpty()) {
                log.error("No authenticated user found");
                throw new RuntimeException("No authenticated user found");
            }

            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> {
                        log.error("User not found with email: {}", userEmail);
                        return new RuntimeException("User not found: " + userEmail);
                    });
            log.info("Found user: {}", user.getEmail());

            Events event = eventsRepository.findById(eventId)
                    .orElseThrow(() -> {
                        log.error("Event not found with ID: {}", eventId);
                        return new RuntimeException("Event not found with ID: " + eventId);
                    });
            log.info("Found event: {}", event.getEventName());

            Optional<ParticipationEvents> existingParticipation = participationEventRepo.findByUserEmail(userEmail)
                    .stream()
                    .filter(p -> p.getEvent() != null && p.getEvent().getIdEvent().equals(eventId))
                    .findFirst();
            log.info("Existing participation: {}", existingParticipation.isPresent() ? "Found" : "Not found");

            if (status == Status.NOT_ATTENDING && existingParticipation.isPresent()) {
                // Delete the participation instead of updating to NOT_ATTENDING
                participationEventRepo.delete(existingParticipation.get());
                log.info("Deleted participation for event: {}", eventId);
                return existingParticipation.get(); // Return the deleted participation for consistency
            }

            ParticipationEvents participation;
            if (existingParticipation.isPresent()) {
                participation = existingParticipation.get();
                participation.setStatus(status);
                log.info("Updating participation status to: {}", status);
            } else {
                participation = new ParticipationEvents();
                participation.setEvent(event);
                participation.setStatus(status);
                participation.setUser(user);
                log.info("Creating new participation with status: {}", status);
            }

            // Validate before saving
            if (participation.getEvent() == null || participation.getUser() == null || participation.getStatus() == null) {
                log.error("Invalid participation data: event={}, user={}, status={}",
                        participation.getEvent(), participation.getUser(), participation.getStatus());
                throw new RuntimeException("Invalid participation data");
            }

            ParticipationEvents savedParticipation = participationEventRepo.save(participation);
            log.info("Saved participation: {}", savedParticipation);

            // Send confirmation email if status is GOING or INTERESTED
            if (status == Status.GOING || status == Status.INTERESTED) {
                try {
                    gmailService.sendEventConfirmationEmail(
                            user.getEmail(),
                            user.getName(),
                            event.getEventName(),
                            event.getStartDate().toInstant().toString(),
                            event.getEndDate().toInstant().toString(),
                            event.getPlace(),
                            status.toString(),
                            event.getIdEvent()
                    );
                    log.info("Sent confirmation email to {} for event {}", user.getEmail(), event.getEventName());
                } catch (Exception e) {
                    log.error("Failed to send confirmation email to {}: {}", user.getEmail(), e.getMessage(), e);
                    // Don't throw exception to avoid failing the participation
                }
            }

            return savedParticipation;
        } catch (Exception e) {
            log.error("Error in participateInEvent for eventId: {}, status: {}, message: {}", eventId, status, e.getMessage(), e);
            throw new RuntimeException("Failed to participate in event: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ParticipationEvents> findByUserEmail(String userEmail) {
        log.info("Finding participations for user: {}", userEmail);
        return participationEventRepo.findByUserEmail(userEmail);
    }
}