package tn.esprit.skillexchange.Controller.GestionEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionEvents.RateEvent;
import tn.esprit.skillexchange.Service.GestionEvents.IRateEventService;

import java.util.List;

@RestController
@RequestMapping("/eventRate")
public class RateEventControler {
   @Autowired
    private IRateEventService rateEventService;


    @RequestMapping("/retrieve-RateEvents")
    public List<RateEvent> getAllRateEvents() {
        return rateEventService.retrieveRateEvent();
    }

    @GetMapping("/retrieveRateEvent/{RateEvent-id}")
    public RateEvent getRateEventById(@PathVariable("RateEvent-id") Long id) {
        return rateEventService.retrieveRateEventById(id);
    }

    @PostMapping("/add-RateEvent")
    public RateEvent addRateEvent(@RequestBody RateEvent rateEvent) {
        return rateEventService.addRateEvent(rateEvent);
    }

    @PutMapping("/modify-RateEvent")
    public RateEvent updatRateEvent(@RequestBody RateEvent rateEvent) {
        return rateEventService.updateRateEvent(rateEvent);
    }


    @DeleteMapping("/removeRateEvent/{RateEvent-id}")
    public void deleteEvent(@PathVariable("RateEvent-id") Long id) {
        rateEventService.removeRateEvent(id);
    }
}
