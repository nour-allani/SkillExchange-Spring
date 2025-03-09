package tn.esprit.skillexchange.Service.GestionEvents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionEvents.RateEvent;
import tn.esprit.skillexchange.Repository.GestionEvents.ParticipationEventRepo;
import tn.esprit.skillexchange.Repository.GestionEvents.RateEventRepo;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateEventServiceImpl implements IRateEventService{
    @Autowired
    RateEventRepo rateEventRepo ;

    @Override
    public List<RateEvent> retrieveRateEvent() {
        return  rateEventRepo.findAll();
    }

    @Override
    public RateEvent addRateEvent(RateEvent rateEvent) {
        return rateEventRepo.save(rateEvent);
    }

    @Override
    public RateEvent updateRateEvent(RateEvent rateEvent) {
        return rateEventRepo.save(rateEvent);
    }

    @Override
    public RateEvent retrieveRateEventById(Long id) {
        return rateEventRepo.findById(id).get();
    }

    @Override
    public void removeRateEvent(Long id) {
        rateEventRepo.deleteById(id);

    }
}
