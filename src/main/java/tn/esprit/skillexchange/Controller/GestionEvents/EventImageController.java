package tn.esprit.skillexchange.Controller.GestionEvents;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.esprit.skillexchange.Entity.GestionEvents.EventImage;
import tn.esprit.skillexchange.Service.GestionEvents.IEventImageService;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eventImage")
public class EventImageController {

    @Autowired
    private IEventImageService imageService;
    @Value("${huggingface.api.key}")
    private String huggingFaceApiKey;


    @GetMapping("/retrieve-EventImage")
    public List<EventImage> getAllEventImage() {
        return imageService.retrieveEventImage();
    }

    @GetMapping("/retrieveEventImage/{EventImage-id}")
    public EventImage getEventImagetById(@PathVariable("EventImage-id") Long id) {
        return imageService.retrieveEventImageById(id);
    }

    @PostMapping("/add-EventImage")
    public EventImage addEventImage(@RequestBody EventImage eventImage) {
        return imageService.addEventImage(eventImage);
    }

    @PatchMapping("/modify-EventImage")
    public EventImage updatEventImage(@RequestBody EventImage eventImage) {
        return imageService.updateEventImage(eventImage);
    }


    @DeleteMapping("/removeEventImage/{EventImage-id}")
    public void deleteEventImage(@PathVariable("EventImage-id") Long id) {
        imageService.removeEventImage(id);
    }


    @PostMapping("/generate-image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, String>> generateImage(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        if (prompt == null || prompt.trim().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Le prompt est requis");
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

            ResponseEntity<byte[]> response = restTemplate.postForEntity(
                    "https://api-inference.huggingface.co/models/stabilityai/stable-diffusion-2-1",
                    entity,
                    byte[].class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String base64Image = Base64.getEncoder().encodeToString(response.getBody());
                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("imageBase64", base64Image);
                return ResponseEntity.ok(successResponse);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Échec de la génération de l'image");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erreur lors de la génération de l'image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}