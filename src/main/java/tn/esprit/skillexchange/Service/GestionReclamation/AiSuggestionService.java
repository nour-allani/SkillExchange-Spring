package tn.esprit.skillexchange.Service.GestionReclamation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;

@Service
public class AiSuggestionService {
    private static final String HF_API_URL = "https://api-inference.huggingface.co/models/facebook/bart-large-mnli";
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(AiSuggestionService.class);
    private static final List<String> CANDIDATE_LABELS = Arrays.asList(
            "Billing Issue", "Delivery Problem", "Technical Defect",
            "Customer Service", "Product Quality", "Website Error",
            "Payment Problem", "Order Cancellation", "Account Issue",
            "Shipping Delay"
    );

    @Value("${huggingface.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public AiSuggestionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(HF_API_URL).build();
    }

    public String getSuggestions(String text) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("inputs", text);
            payload.put("parameters", Map.of(
                    "candidate_labels", new String[]{
                            "technical issue",
                            "payment problem",
                            "account access",
                            "content issue",
                            "download problem"
                    }
            ));

            return webClient.post()
                    .header("Authorization", "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(status -> status.isError(), response -> {
                        // Handle Hugging Face API errors (e.g., model loading)
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Hugging Face Error: " + errorBody)));
                    })
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            // Return a structured JSON error for the frontend
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    public Map<String, Double> analyzeTitles(List<String> titles) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            Map<String, Object> request = new HashMap<>();
            request.put("inputs", titles);
            request.put("parameters", Map.of(
                    "candidate_labels", CANDIDATE_LABELS,
                    "multi_label", true
            ));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

            // Change response type to List
            ResponseEntity<List> response = restTemplate.postForEntity(
                    "https://api-inference.huggingface.co/models/facebook/bart-large-mnli",
                    entity,
                    List.class
            );

            return processResponse(response.getBody(), titles.size());
        } catch (Exception e) {
            logger.error("AI Analysis failed: ", e);
            return Collections.emptyMap();
        }
    }

    private Map<String, Double> processResponse(List<Map<String, Object>> response, int totalTitles) {
        Map<String, Double> aggregatedScores = new HashMap<>();

        response.forEach(result -> {
            List<String> labels = (List<String>) result.get("labels");
            List<Double> scores = (List<Double>) result.get("scores");

            for (int i = 0; i < labels.size(); i++) {
                String label = labels.get(i);
                double score = scores.get(i);
                aggregatedScores.put(label, aggregatedScores.getOrDefault(label, 0.0) + score);
            }
        });

        // Calculate average scores
        aggregatedScores.replaceAll((k, v) -> Math.round((v / totalTitles) * 10000) / 100.0);

        return aggregatedScores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
