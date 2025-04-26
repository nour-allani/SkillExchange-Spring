package tn.esprit.skillexchange.Service.GestionEvents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionEvents.EventComment;
import tn.esprit.skillexchange.Repository.GestionEvents.EventCommentRepo;
import tn.esprit.skillexchange.Repository.GestionEvents.EventImageRepo;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventCommentServiceImpl implements IEventCommentService{
    @Autowired
    EventCommentRepo eventCommentRepo ;


    @Override
    public List<EventComment> retrieveEventComment() {
        return eventCommentRepo.findAll();
    }

    @Override
    public EventComment addEventComment(EventComment eventComment) {
        return eventCommentRepo.save(eventComment);
    }

    @Override
    public EventComment updateEventComment(EventComment eventComment) {
        return eventCommentRepo.save(eventComment);
    }

    @Override
    public EventComment retrieveEventCommentById(Long id) {
        return eventCommentRepo.findById(id).get();
    }

    @Override
    public void removeEventComment(Long id) {
        eventCommentRepo.deleteById(id);

    }
}