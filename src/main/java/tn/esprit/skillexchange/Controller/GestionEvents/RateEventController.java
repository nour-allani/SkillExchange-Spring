package tn.esprit.skillexchange.Controller.GestionEvents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionEvents.RateEvent;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Service.GestionEvents.IRateEventService;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Repository.GestionEvents.EventRepo;

import java.util.List;

@RestController
@RequestMapping("/eventRate")
public class RateEventController {
    @Autowired
    private IRateEventService rateEventService;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private EventRepo eventRepo;

    @GetMapping("/retrieve-RateEvents")
    public ResponseEntity<List<RateEvent>> getAllRateEvents() {
        try {
            List<RateEvent> rateEvents = rateEventService.retrieveRateEvent();
            return new ResponseEntity<>(rateEvents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/retrieveRateEvent/{RateEvent-id}")
    public ResponseEntity<RateEvent> getRateEventById(@PathVariable("RateEvent-id") Long id) {
        try {
            RateEvent rateEvent = rateEventService.retrieveRateEventById(id);
            if (rateEvent != null) {
                return new ResponseEntity<>(rateEvent, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/retrieveRateEventsByEvent/{eventId}")
    public ResponseEntity<List<RateEvent>> getRateEventsByEventId(@PathVariable("eventId") Long eventId) {
        List<RateEvent> rateEvents = rateEventService.retrieveRateEventsByEventId(eventId);

        // 🔧 Correction ici : on recharge les users à chaque fois pour éviter user.name null
        rateEvents.forEach(rate -> {
            if (rate.getUser() != null && rate.getUser().getId() != 0) {
                User fullUser = userRepository.findById(rate.getUser().getId()).orElse(null);
                rate.setUser(fullUser);
            }

            if (rate.getEvent() != null && rate.getEvent().getIdEvent() != null) {
                Events fullEvent = eventRepo.findById(rate.getEvent().getIdEvent()).orElse(null);
                rate.setEvent(fullEvent);
            }
        });

        return new ResponseEntity<>(rateEvents, HttpStatus.OK);
    }

    @PostMapping("/add-RateEvent")
    public ResponseEntity<RateEvent> addRateEvent(@RequestBody RateEvent rateEvent) {
        try {
            System.out.println("Received RateEvent:");
            System.out.println("User ID: " + (rateEvent.getUser() != null ? rateEvent.getUser().getId() : "null"));
            System.out.println("Event ID: " + (rateEvent.getEvent() != null ? rateEvent.getEvent().getIdEvent() : "null"));
            System.out.println("Content: " + rateEvent.getContent());
            System.out.println("Rating: " + rateEvent.getRating());

            if (rateEvent.getUser() == null || rateEvent.getUser().getId() == 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (rateEvent.getEvent() == null || rateEvent.getEvent().getIdEvent() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            User user = userRepository.findById(rateEvent.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            Events event = eventRepo.findById(rateEvent.getEvent().getIdEvent())
                    .orElseThrow(() -> new IllegalArgumentException("Event not found"));

            rateEvent.setUser(user);
            rateEvent.setEvent(event);

            RateEvent savedRateEvent = rateEventService.addRateEvent(rateEvent);
            return new ResponseEntity<>(savedRateEvent, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // log the error to console
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping("/modify-RateEvent")
    public ResponseEntity<RateEvent> updateRateEvent(@RequestBody RateEvent rateEvent) {
        try {
            if (rateEvent.getUser() != null && rateEvent.getUser().getId() != 0) {
                User user = userRepository.findById(rateEvent.getUser().getId())
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
                rateEvent.setUser(user);
            }

            if (rateEvent.getEvent() != null && rateEvent.getEvent().getIdEvent() != null) {
                Events event = eventRepo.findById(rateEvent.getEvent().getIdEvent())
                        .orElseThrow(() -> new IllegalArgumentException("Event not found"));
                rateEvent.setEvent(event);
            }

            RateEvent updatedRateEvent = rateEventService.updateRateEvent(rateEvent);
            if (updatedRateEvent != null) {
                return new ResponseEntity<>(updatedRateEvent, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/removeRateEvent/{RateEvent-id}")
    public ResponseEntity<Void> deleteRateEvent(@PathVariable("RateEvent-id") Long id) {
        try {
            rateEventService.removeRateEvent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/retrieveRateEventByUserAndEvent")
    public ResponseEntity<RateEvent> getRateEventByUserAndEvent(
            @RequestParam("userId") Long userId,
            @RequestParam("eventId") Long eventId) {
        try {
            RateEvent rateEvent = rateEventService.retrieveRateEventByUserAndEvent(userId, eventId);
            if (rateEvent != null) {
                return new ResponseEntity<>(rateEvent, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
