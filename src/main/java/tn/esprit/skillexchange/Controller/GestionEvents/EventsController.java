package tn.esprit.skillexchange.Controller.GestionEvents;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.esprit.skillexchange.Entity.GestionEvents.EventImage;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Service.GestionEvents.IEventsService;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
@Slf4j
public class EventsController {

    @Autowired
    private IEventsService eventsService;

    @Value("${huggingface.api.key}")
    private String huggingFaceApiKey;

    @GetMapping("/retrieve-Events")
    public List<Events> getAllEvents() {
        return eventsService.retrieveEvents();
    }

    @GetMapping("/retrieveEvents/{Event-id}")
    public Events getEventById(@PathVariable("Event-id") Long id) {
        return eventsService.retrieveEventById(id);
    }

    @PostMapping("/add-Event")
    @PreAuthorize("isAuthenticated()")
    public Events addEvent(@RequestBody Events events) {
        if (events.getImages() != null) {
            for (EventImage img : events.getImages()) {
                img.setEvent(events);
            }
        }
        return eventsService.addEvent(events);
    }

    @PatchMapping("/modify-Event")
    @PreAuthorize("isAuthenticated()")
    public Events updateEvent(@RequestBody Events events) {
        if (events.getImages() != null) {
            for (EventImage img : events.getImages()) {
                img.setEvent(events);
            }
        }
        return eventsService.updateEvent(events);
    }

    @DeleteMapping("/removeEvent/{Event-id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteEvent(@PathVariable("Event-id") Long id) {
        eventsService.removeEvent(id);
    }

    @PostMapping("/generate-image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, String>> generateImage(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        log.info("Generating image with prompt: {}", prompt);
        if (prompt == null || prompt.trim().isEmpty()) {
            log.error("Prompt is required");
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Prompt is required");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + huggingFaceApiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = new HashMap<>();
            body.put("inputs", prompt);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
            log.info("Sending request to Hugging Face API with key: {}", huggingFaceApiKey.substring(0, 5) + "...");

            ResponseEntity<byte[]> response = restTemplate.postForEntity(
                    "https://api-inference.huggingface.co/models/stabilityai/stable-diffusion-xl-base-1.0",
                    entity,
                    byte[].class
            );

            log.info("Received response with status: {}", response.getStatusCode());
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String base64Image = Base64.getEncoder().encodeToString(response.getBody());
                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("imageBase64", base64Image);
                return ResponseEntity.ok(successResponse);
            } else {
                log.error("Failed to generate image, status: {}", response.getStatusCode());
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Failed to generate image");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        } catch (Exception e) {
            log.error("Error generating image: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error generating image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}