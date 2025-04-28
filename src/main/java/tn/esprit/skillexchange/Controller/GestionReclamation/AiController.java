package tn.esprit.skillexchange.Controller.GestionReclamation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Service.GestionReclamation.AiSuggestionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
public class AiController {
    private final AiSuggestionService aiService;

    @Autowired
    public AiController(AiSuggestionService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/suggestions")
    public ResponseEntity<String> getSuggestions(@RequestBody String text) {
        return ResponseEntity.ok(aiService.getSuggestions(text));
    }

    @PostMapping("/analyze-titles")
    public ResponseEntity<Map<String, Double>> analyzeTitles(@RequestBody List<String> titles) {
        Map<String, Double> topIssues = aiService.analyzeTitles(titles);
        return ResponseEntity.ok(topIssues);
    }
    /*@PostMapping("/analyze-titles")
    public ResponseEntity<Map<String, Double>> analyzeTitles(
            @RequestBody List<String> titles,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("error", 401.0)
            );
        }

        // Optional: Add basic token validation
        String token = authHeader.substring(7);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("error", 401.0)
            );
        }

        return ResponseEntity.ok(aiService.analyzeTitles(titles));
    }*/
}
