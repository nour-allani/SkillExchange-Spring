package tn.esprit.skillexchange.Service.GestionEvents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Repository.GestionEvents.EventRepo;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements IEventsService {
    @Autowired

    EventRepo eventRepo ;

    @Override
    public List<Events> retrieveEvents() {
        return  eventRepo.findAll();
    }

    @Override
    public Events addEvent(Events event) {
        return eventRepo.save(event);
    }

    @Override
    public Events updateEvent(Events event) {
        return eventRepo.save(event);
    }

    @Override
    public Events retrieveEventById(Long id) {
        return eventRepo.findById(id).get();
    }

    @Override
    public void removeEvent(Long id) {
    eventRepo.deleteById(id);
    }
}
