package tn.esprit.skillexchange.Service.GestionForumPost;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ToxicityDetectionService {

    @Value("${huggingface.api.token}")
    private String apiToken;

    private static final String HUGGINGFACE_API_URL = "https://api-inference.huggingface.co/models/unitary/toxic-bert";

    public Map<String, Double> analyzeToxicity(String text) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payload = new HashMap<>();
        payload.put("inputs", text);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<Map[]> response = restTemplate.exchange(
                HUGGINGFACE_API_URL,
                HttpMethod.POST,
                entity,
                Map[].class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().length > 0) {
            Map<String, Double> toxicityScores = new HashMap<>();
            Map<String, Object> output = response.getBody()[0];
            Map<String, Object> scores = (Map<String, Object>) output.get("scores");

            for (Map.Entry<String, Object> entry : scores.entrySet()) {
                toxicityScores.put(entry.getKey(), (Double) entry.getValue());
            }

            return toxicityScores;
        } else {
            throw new RuntimeException("Erreur lors de l'analyse de la toxicité");
        }
    }
}
