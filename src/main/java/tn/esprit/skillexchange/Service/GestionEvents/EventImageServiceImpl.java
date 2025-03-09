package tn.esprit.skillexchange.Service.GestionEvents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionEvents.EventImage;
import tn.esprit.skillexchange.Repository.GestionEvents.EventImageRepo;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventImageServiceImpl implements IEventImageService{
    @Autowired
    EventImageRepo eventImageRepo ;

    @Override
    public List<EventImage> retrieveEventImage() {
        return eventImageRepo.findAll();
    }

    @Override
    public EventImage addEventImage(EventImage eventImage) {
        return eventImageRepo.save(eventImage);
    }

    @Override
    public EventImage updateEventImage(EventImage eventImage) {
        return eventImageRepo.save(eventImage);
    }

    @Override
    public EventImage retrieveEventImageById(Long id) {
        return eventImageRepo.findById(id).get();
    }

    @Override
    public void removeEventImage(Long id) {
        eventImageRepo.deleteById(id);

    }
}
