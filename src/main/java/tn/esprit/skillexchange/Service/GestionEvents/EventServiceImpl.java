package tn.esprit.skillexchange.Service.GestionEvents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.skillexchange.Entity.GestionEvents.EventImage;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Repository.GestionEvents.EventImageRepo;
import tn.esprit.skillexchange.Repository.GestionEvents.EventRepo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements IEventsService {
    @Autowired
    EventRepo eventRepo;

    @Autowired
    EventImageRepo eventImageRepo;

    @Override
    public List<Events> retrieveEvents() {
        return eventRepo.findAll();
    }

    @Override
    public Events addEvent(Events event) {
        log.info("Adding event: {}", event.getEventName());
        return eventRepo.save(event);
    }



    @Override
    @Transactional
    public Events updateEvent(Events event) {
        log.info("Updating event with ID: {}", event.getIdEvent());

        // Retrieve the existing event
        Events existingEvent = eventRepo.findById(event.getIdEvent())
                .orElseThrow(() -> {
                    log.error("Event not found with id: {}", event.getIdEvent());
                    return new IllegalArgumentException("Event not found with id: " + event.getIdEvent());
                });

        // Update event fields
        existingEvent.setEventName(event.getEventName());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setStartDate(event.getStartDate());
        existingEvent.setEndDate(event.getEndDate());
        existingEvent.setPlace(event.getPlace());
        existingEvent.setNbr_max(event.getNbr_max());
        existingEvent.setUser(event.getUser());

        // Handle images
        Set<EventImage> newImages = event.getImages();
        Set<EventImage> existingImages = existingEvent.getImages();

        log.info("Existing images: {} (count: {}), New images: {} (count: {})",
                existingImages != null ? existingImages.stream().map(EventImage::getIdImage).collect(Collectors.toList()) : "null",
                existingImages != null ? existingImages.size() : 0,
                newImages != null ? newImages.stream().map(EventImage::getIdImage).collect(Collectors.toList()) : "null",
                newImages != null ? newImages.size() : 0);

        // If newImages is null or empty, delete all existing images
        if (newImages == null || newImages.isEmpty()) {
            if (existingImages != null && !existingImages.isEmpty()) {
                log.info("Deleting all {} existing images for event ID: {}", existingImages.size(), event.getIdEvent());
                for (EventImage image : existingImages) {
                    log.debug("Deleting image ID: {}", image.getIdImage());
                    eventImageRepo.deleteById(image.getIdImage());
                }
                existingImages.clear();
            }
            existingEvent.setImages(null);
        } else {
            // Create a set of new image IDs (exclude null IDs for new images)
            Set<Long> newImageIds = newImages.stream()
                    .map(EventImage::getIdImage)
                    .filter(id -> id != null)
                    .collect(Collectors.toSet());

            // Delete images that are no longer present
            if (existingImages != null && !existingImages.isEmpty()) {
                Set<EventImage> imagesToRemove = existingImages.stream()
                        .filter(image -> !newImageIds.contains(image.getIdImage()))
                        .collect(Collectors.toSet());
                for (EventImage image : imagesToRemove) {
                    log.debug("Deleting image ID: {} (not in new images)", image.getIdImage());
                    eventImageRepo.deleteById(image.getIdImage());
                }
                existingImages.removeAll(imagesToRemove);
            }

            // Add or update new images
            for (EventImage newImage : newImages) {
                newImage.setEvent(existingEvent);
                if (newImage.getIdImage() == null) {
                    // New image
                    log.debug("Adding new image for event ID: {}", event.getIdEvent());
                    EventImage savedImage = eventImageRepo.save(newImage);
                    if (existingImages == null) {
                        existingEvent.setImages(Set.of(savedImage));
                    } else {
                        existingImages.add(savedImage);
                    }
                } else {
                    // Update existing image
                    existingImages.stream()
                            .filter(img -> img.getIdImage().equals(newImage.getIdImage()))
                            .findFirst()
                            .ifPresent(existingImage -> {
                                log.debug("Updating image ID: {}", existingImage.getIdImage());
                                existingImage.setImages(newImage.getImages());
                                eventImageRepo.save(existingImage);
                            });
                }
            }
        }

        // Save and return the updated event
        Events updatedEvent = eventRepo.save(existingEvent);
        log.info("Event updated successfully, ID: {}, Images: {}",
                updatedEvent.getIdEvent(),
                updatedEvent.getImages() != null ? updatedEvent.getImages().stream().map(EventImage::getIdImage).collect(Collectors.toList()) : "none");
        return updatedEvent;
    }

    @Override
    public Events retrieveEventById(Long id) {
        return eventRepo.findById(id).orElseThrow(() -> {
            log.error("Event not found with id: {}", id);
            return new IllegalArgumentException("Event not found with id: " + id);
        });
    }

    @Override
    public void removeEvent(Long id) {
        log.info("Deleting event with ID: {}", id);
        eventRepo.deleteById(id);
    }
}