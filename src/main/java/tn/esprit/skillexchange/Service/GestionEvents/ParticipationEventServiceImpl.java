package tn.esprit.skillexchange.Service.GestionEvents;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ParticipationEventServiceImpl implements IParticipationEventsService {

    @Autowired
    private ParticipationEventRepo participationEventsRepository;

    @Autowired
    private EventRepo eventsRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private GmailService gmailService;

    @Override
    public ParticipationEvents participateInEvent(Long eventId, String userEmail, Status status) {
        Optional<Events> eventOpt = eventsRepository.findById(eventId);
        Optional<User> userOpt = userRepository.findByEmail(userEmail);

        if (eventOpt.isEmpty() || userOpt.isEmpty()) {
            throw new IllegalArgumentException("Event or User not found");
        }

        Events event = eventOpt.get();
        User user = userOpt.get();

        ParticipationEvents participation = new ParticipationEvents();
        participation.setEvent(event);
        participation.setUser(user);
        participation.setStatus(status);

        ParticipationEvents savedParticipation = participationEventsRepository.save(participation);
        log.info("Saved participation: {}", savedParticipation);

        // Send confirmation email only if status is GOING
        if (status == Status.GOING) {
            try {
                String userName = user.getUsername() != null ? user.getUsername() : userEmail;
                gmailService.sendEventConfirmationEmail(
                        userEmail,
                        userName,
                        event.getEventName(),
                        event.getStartDate().toString(),
                        event.getEndDate().toString(),
                        event.getPlace(),
                        status.toString(),
                        eventId,
                        savedParticipation.getIdparticipant()
                );
                log.info("Sent confirmation email to {} for event {}", userEmail, event.getEventName());
            } catch (Exception e) {
                log.error("Failed to send confirmation email to {}: {}", userEmail, e.getMessage(), e);
            }
        } else {
            log.info("No confirmation email sent to {} for event {} (status: {})", userEmail, event.getEventName(), status);
        }

        return savedParticipation;
    }

    @Override
    public List<ParticipationEvents> retrieveParticipationEvents() {
        return participationEventsRepository.findAll();
    }

    @Override
    public ParticipationEvents retrieveParticipationEventsById(Long id) {
        return participationEventsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participation not found"));
    }

    @Override
    public ParticipationEvents addParticipationEvents(ParticipationEvents participationEvents) {
        return participationEventsRepository.save(participationEvents);
    }

    @Override
    public ParticipationEvents updateParticipationEvents(ParticipationEvents participationEvents) {
        if (!participationEventsRepository.existsById(participationEvents.getIdparticipant())) {
            throw new IllegalArgumentException("Participation not found");
        }
        return participationEventsRepository.save(participationEvents);
    }

    @Override
    public void removeParticipationEvents(Long id) {
        if (!participationEventsRepository.existsById(id)) {
            throw new IllegalArgumentException("Participation not found");
        }
        participationEventsRepository.deleteById(id);
    }

    @Override
    public List<ParticipationEvents> findByUserEmail(String userEmail) {
        return participationEventsRepository.findByUserEmail(userEmail);
    }
}