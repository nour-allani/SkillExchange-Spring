package tn.esprit.skillexchange.Service.GestionForumPost;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class HuggingFaceService {

    private final WebClient webClient;

    @Value("${huggingface.api.key}")
    private String apiKey;

    public HuggingFaceService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api-inference.huggingface.co/models/google/flan-t5-base").build();
    }

    public Mono<String> generateComment(String postContent) {
        Map<String, Object> body = new HashMap<>();
        body.put("inputs", "Please generate a detailed and full comment for the following post and never repeat any comment more than one: " + postContent);
// Ajouter les paramètres 'temperature' et 'top_p'
        body.put("temperature", 0.7);  // Ajuster la température pour plus de variété
        body.put("top_p", 0.9);         // Ajuster top_p pour contrôler la diversité
        return webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(this::extractCommentFromResponse)
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just("Error generating comment.");
                });
    }

    private Mono<String> extractCommentFromResponse(String response) {
        JSONArray jsonArray = new JSONArray(response);
        if (jsonArray.length() > 0) {
            JSONObject firstObject = jsonArray.getJSONObject(0);
            return Mono.just(firstObject.getString("generated_text"));
        } else {
            return Mono.just("No comment generated.");
        }
    }
}
