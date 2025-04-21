package tn.esprit.skillexchange.Service.GestionEvents;

import tn.esprit.skillexchange.Entity.GestionEvents.RateEvent;

import java.util.List;

public interface IRateEventService {


    List<RateEvent> retrieveRateEvent();
    RateEvent addRateEvent(RateEvent rateEvent);
    RateEvent updateRateEvent(RateEvent rateEvent);
    RateEvent retrieveRateEventById(Long id);
    void removeRateEvent(Long id);
}