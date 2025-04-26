package tn.esprit.skillexchange.Service.GestionEvents;

import tn.esprit.skillexchange.Entity.GestionEvents.EventComment;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;

import java.util.List;

public interface IEventCommentService {



    List<EventComment> retrieveEventComment();
    EventComment addEventComment(EventComment eventComment);
    EventComment updateEventComment(EventComment eventComment);
    EventComment retrieveEventCommentById(Long id);
    void removeEventComment(Long id);





}