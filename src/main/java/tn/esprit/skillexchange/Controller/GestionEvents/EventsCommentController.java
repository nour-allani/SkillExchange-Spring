package tn.esprit.skillexchange.Controller.GestionEvents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionEvents.EventComment;
import tn.esprit.skillexchange.Service.GestionEvents.IEventCommentService;

import java.util.List;

@RestController
@RequestMapping("/eventComment")
public class EventsCommentController {

    @Autowired
    private IEventCommentService eventCommentService;


    @GetMapping("/retrieve-EventComment")
    public List<EventComment> getAllEventComments() {
        return eventCommentService.retrieveEventComment();
    }

    @GetMapping("/retrieveEventComment/{EventComment-id}")
    public EventComment getEventCommenttById(@PathVariable("EventComment-id") Long id) {
        return eventCommentService.retrieveEventCommentById(id);
    }

    @PostMapping("/add-EventComment")
    public EventComment addEventComment(@RequestBody EventComment eventComment) {
        return eventCommentService.addEventComment(eventComment);
    }

    @PatchMapping("/modify-EventComment")
    public EventComment updatEventImage(@RequestBody EventComment eventComment) {
        return eventCommentService.updateEventComment(eventComment);
    }


    @DeleteMapping("/removeEventComment/{EventComment-id}")
    public void deleteEventComment(@PathVariable("EventComment-id") Long id) {
        eventCommentService.removeEventComment(id);
    }
}