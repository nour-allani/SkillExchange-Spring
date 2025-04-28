package tn.esprit.skillexchange.Service.GestionEvents;

import tn.esprit.skillexchange.Entity.GestionEvents.Events;

import java.util.List;

public interface IEventsService {


    List<Events> retrieveEvents();
    Events addEvent(Events event);
    Events updateEvent(Events event);
    Events retrieveEventById(Long id);
    void removeEvent(Long id);



}
