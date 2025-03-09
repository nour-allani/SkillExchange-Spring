package tn.esprit.skillexchange.Controller.GestionEvents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Service.GestionEvents.IParticipationEventsService;

import java.util.List;

@RestController
@RequestMapping("/participationEvents")
public class ParticipationEventsController {
    @Autowired
    private IParticipationEventsService  participationEventsService;


    @RequestMapping("/retrieve-ParticipationEvents")
    public List<ParticipationEvents> getAllParticipationEvents() {
        return participationEventsService.retrieveParticipationEvents();
    }

    @GetMapping("/retrieveParticipationEvents/{ParticipationEvents-id}")
    public ParticipationEvents getParticipationEventsById(@PathVariable("ParticipationEvents-id") Long id) {
        return participationEventsService.retrieveParticipationEventsById(id);
    }

    @PostMapping("/add-ParticipationEvents")
    public ParticipationEvents addParticipationEvents(@RequestBody ParticipationEvents participationEvents) {
        return participationEventsService.addParticipationEvents(participationEvents);
    }

    @PutMapping("/modify-ParticipationEvents")
    public ParticipationEvents updatParticipationEvents(@RequestBody ParticipationEvents participationEvents) {
        return participationEventsService.updateParticipationEvents(participationEvents);
    }


    @DeleteMapping("/removeParticipationEvents/{ParticipationEvents-id}")
    public void deleteParticipationEvents(@PathVariable("ParticipationEvents-id") Long id) {
        participationEventsService.removeParticipationEvents(id);
    }
}
