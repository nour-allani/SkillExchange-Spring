package tn.esprit.skillexchange.Controller.GestionEvents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Service.GestionEvents.IEventsService;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {



    @Autowired
    private IEventsService eventsService;


    @RequestMapping("/retrieve-Events")
    public List<Events> getAllEvents() {
        return eventsService.retrieveEvents();
    }

    @GetMapping("/retrieveEvents/{Event-id}")
    public Events getEventById(@PathVariable("Event-id") Long id) {
        return eventsService.retrieveEventById(id);
    }

    @PostMapping("/add-Event")
    public Events addEvent(@RequestBody Events events) {
        return eventsService.addEvent(events);
    }

    @PutMapping("/modify-Event")
    public Events updatEvent(@RequestBody Events events) {
        return eventsService.updateEvent(events);
    }


    @DeleteMapping("/removeEvent/{Event-id}")
    public void deleteEvent(@PathVariable("Event-id") Long id) {
        eventsService.removeEvent(id);
    }
}
