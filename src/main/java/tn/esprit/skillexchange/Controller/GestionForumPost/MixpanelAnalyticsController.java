package tn.esprit.skillexchange.Controller.GestionForumPost;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/mixpanel")
@CrossOrigin(origins = "*") // Permettre à Angular d'accéder
public class MixpanelAnalyticsController {
    @Value("${mixpanel.api.secret}")
    private String mixpanelApiSecret;

    private final String MIXPANEL_BASE_URL = "https://mixpanel.com/api/2.0/events/";
    private static final Logger logger = Logger.getLogger(MixpanelAnalyticsController.class.getName());

    @GetMapping("/events")
    public ResponseEntity<String> getEvents(
            @RequestParam String event,
            @RequestParam String from_date,
            @RequestParam String to_date) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(mixpanelApiSecret, ""); // Authentification Basic
        headers.setAccept(MediaType.parseMediaTypes("application/json"));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MIXPANEL_BASE_URL)
                .queryParam("event", event)
                .queryParam("from_date", from_date)
                .queryParam("to_date", to_date);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else {
                // Si l'API Mixpanel renvoie un code d'erreur, renvoyer un message approprié.
                return new ResponseEntity<>("Erreur lors de la récupération des événements", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Capture des exceptions et renvoi d'une réponse d'erreur.
            return new ResponseEntity<>("Erreur de connexion à l'API Mixpanel", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
