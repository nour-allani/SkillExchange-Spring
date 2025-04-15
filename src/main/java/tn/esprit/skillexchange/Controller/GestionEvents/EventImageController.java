package tn.esprit.skillexchange.Controller.GestionEvents;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionEvents.EventImage;
import tn.esprit.skillexchange.Service.GestionEvents.IEventImageService;

import java.util.List;

@RestController
@RequestMapping("/eventImage")
public class EventImageController {

    @Autowired
    private IEventImageService imageService;


    @GetMapping("/retrieve-EventImage")
    public List<EventImage> getAllEventImage() {
        return imageService.retrieveEventImage();
    }

    @GetMapping("/retrieveEventImage/{EventImage-id}")
    public EventImage getEventImagetById(@PathVariable("EventImage-id") Long id) {
        return imageService.retrieveEventImageById(id);
    }

    @PostMapping("/add-EventImage")
    public EventImage addEventImage(@RequestBody EventImage eventImage) {
        return imageService.addEventImage(eventImage);
    }

    @PatchMapping("/modify-EventImage")
    public EventImage updatEventImage(@RequestBody EventImage eventImage) {
        return imageService.updateEventImage(eventImage);
    }


    @DeleteMapping("/removeEventImage/{EventImage-id}")
    public void deleteEventImage(@PathVariable("EventImage-id") Long id) {
        imageService.removeEventImage(id);
    }
}
