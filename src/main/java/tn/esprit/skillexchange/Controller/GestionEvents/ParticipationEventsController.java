package tn.esprit.skillexchange.Controller.GestionEvents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Entity.GestionEvents.Status;
import tn.esprit.skillexchange.Service.GestionEvents.IParticipationEventsService;

import java.util.List;

@RestController
@RequestMapping("/participationEvents")
public class ParticipationEventsController {
    @Autowired
    private IParticipationEventsService  participationEventsService;


    @GetMapping("/retrieve-ParticipationEvents")
    public List<ParticipationEvents> getAllParticipationEvents() {
        return participationEventsService.retrieveParticipationEvents();
    }

    @GetMapping("/retrieveParticipationEvents/{ParticipationEvents-id}")
    public ParticipationEvents getParticipationEventsById(@PathVariable("ParticipationEvents-id") Long id) {
        return participationEventsService.retrieveParticipationEventsById(id);
    }

    @PostMapping("/add-ParticipationEvents")
    public ParticipationEvents addParticipationEvents(@RequestBody ParticipationEvents participationEvents) {
        try {
            // Vérifiez si participationEvents est bien reçu
            System.out.println("Participation Event: " + participationEvents);
            return participationEventsService.addParticipationEvents(participationEvents);
        } catch (Exception e) {
            // Log d'erreur pour mieux comprendre la cause du problème
            System.err.println("Erreur lors de l'ajout de la participation : " + e.getMessage());
            throw e;  // Relancer l'exception pour générer une erreur HTTP 500
        }
    }

    @PatchMapping("/modify-ParticipationEvents")
    public ParticipationEvents updatParticipationEvents(@RequestBody ParticipationEvents participationEvents) {
        return participationEventsService.updateParticipationEvents(participationEvents);
    }


    @DeleteMapping("/removeParticipationEvents/{ParticipationEvents-id}")
    public void deleteParticipationEvents(@PathVariable("ParticipationEvents-id") Long id) {
        participationEventsService.removeParticipationEvents(id);
    }


    // ✅ Nouveau endpoint pour participer à un événement
    @PostMapping("/participate/{eventId}/{status}")
    public ParticipationEvents participateInEvent(
            @PathVariable Long eventId,
            @PathVariable Status status) {
        return participationEventsService.participateInEvent(eventId, status);
    }
}
