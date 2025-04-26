package tn.esprit.skillexchange.Service.GestionEvents;
import tn.esprit.skillexchange.Entity.GestionEvents.EventImage;

import java.util.List;

public interface IEventImageService {



    List<EventImage> retrieveEventImage();
    EventImage addEventImage(EventImage eventImage);
    EventImage updateEventImage(EventImage event);
    EventImage retrieveEventImageById(Long id);
    void removeEventImage(Long id);

}