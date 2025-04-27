package tn.esprit.skillexchange.Service.GestionProduit.ProductIA;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiReviewService implements IAiReviewService {

    @Value("${clarifai.api.key.review}")
    private String clarifaiReviewApiKey; // ✅ Correct ici

    private static final String CLARIFAI_TEXT_API_URL = "https://api.clarifai.com/v2/models/text-sentiment-classification/outputs";

    public String analyzeReview(String comment) {

        RestTemplate restTemplate = new RestTemplate();

        String body = "{ \"inputs\": [ { \"data\": { \"text\": { \"raw\": \"" + comment + "\" } } } ] }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // ✅ Correction ici
        headers.set("Authorization", "Key " + clarifaiReviewApiKey); // ✅ Correction ici

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                CLARIFAI_TEXT_API_URL,
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null) return "UNKNOWN";

        Map<String, Object> firstOutput = ((List<Map<String, Object>>) responseBody.get("outputs")).get(0);
        Map<String, Object> data = (Map<String, Object>) firstOutput.get("data");
        List<Map<String, Object>> concepts = (List<Map<String, Object>>) data.get("concepts");

        if (concepts == null || concepts.isEmpty()) return "UNKNOWN";

        String sentiment = (String) concepts.get(0).get("name"); // positive, negative, neutral
        return sentiment.toUpperCase(); // ex: "POSITIVE"
    }
}
