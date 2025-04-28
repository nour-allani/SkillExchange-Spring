package tn.esprit.skillexchange.Service.GestionFormation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import tn.esprit.skillexchange.Entity.GestionFormation.OllamaRequest;
import tn.esprit.skillexchange.Entity.GestionFormation.OllamaResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class OllamaService {
    private final RestTemplate restTemplate;
    private static final String OLLAMA_API_URL = "http://localhost:11435/api/generate";

    public OllamaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OllamaResponse generateCourseContent(String prompt) {
        OllamaRequest request = new OllamaRequest();
        request.setModel("mistral");
        request.setPrompt(prompt);
        request.setStream(false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OllamaRequest> entity = new HttpEntity<>(request, headers);
        return restTemplate.postForObject(OLLAMA_API_URL, entity, OllamaResponse.class);
    }
}