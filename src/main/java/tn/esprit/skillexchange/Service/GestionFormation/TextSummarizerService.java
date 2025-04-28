package tn.esprit.skillexchange.Service.GestionFormation;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;

@Service
public class TextSummarizerService {
//    @Value("${huggingface.api.key}") // Ajoute ta clé API Hugging Face dans application.properties
//    private String apiKey;


    private final String apiKey = "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";  // Remplace avec ta clé d'API Hugging Face

    public String summarizeText(String text) {
        // URL de l'API Hugging Face pour BART
        String url = "https://api-inference.huggingface.co/models/facebook/bart-large-cnn";

        // Créer l'objet RestTemplate pour effectuer les requêtes HTTP
        RestTemplate restTemplate = new RestTemplate();

        // Ajouter l'en-tête Authorization pour l'API
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Créer le corps de la requête JSON (données à envoyer)
        String body = "{\"inputs\": \"" + text + "\"}";

        // Créer l'entité HTTP avec les en-têtes et le corps de la requête
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        // Effectuer une requête POST vers l'API Hugging Face
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Retourner la réponse du corps de la requête
        return response.getBody();
    }
}
