package tn.esprit.skillexchange.Service.GestionEvents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionEvents.RateEvent;
import tn.esprit.skillexchange.Repository.GestionEvents.RateEventRepo;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateEventServiceImpl implements IRateEventService {
    @Autowired
    RateEventRepo rateEventRepo;

    @Override
    public List<RateEvent> retrieveRateEvent() {
        return rateEventRepo.findAll();
    }

    @Override
    public RateEvent addRateEvent(RateEvent rateEvent) {
        // Ensure idRate is null for new entities to let the database generate it
        rateEvent.setIdRate(null);
        rateEvent.setCreatedAt(new Date());
        rateEvent.setUpdatedAt(new Date());
        return rateEventRepo.save(rateEvent);
    }

    @Override
    public RateEvent updateRateEvent(RateEvent rateEvent) {
        rateEvent.setUpdatedAt(new Date());
        return rateEventRepo.save(rateEvent);
    }

    @Override
    public RateEvent retrieveRateEventById(Long id) {
        return rateEventRepo.findById(id).orElse(null);
    }

    @Override
    public void removeRateEvent(Long id) {
        rateEventRepo.deleteById(id);
    }

    @Override
    public List<RateEvent> retrieveRateEventsByEventId(Long eventId) {
        return rateEventRepo.findByEventIdEvent(eventId);
    }

    @Override
    public RateEvent retrieveRateEventByUserAndEvent(Long userId, Long eventId) {
        return rateEventRepo.findByUserIdAndEventIdEvent(userId, eventId).orElse(null);
    }
}