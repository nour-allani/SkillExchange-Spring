package tn.esprit.skillexchange.Service.GestionProduit.ProductIA;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AiProductService {

    @Value("${clarifai.api.key}")
    private String clarifaiApiKey;

    private static final String CLARIFAI_API_URL = "https://api.clarifai.com/v2/models/general-image-recognition/outputs";

    public Map<String, Object> analyzeImages(MultipartFile[] images) {

        Map<String, Object> result = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        if (images == null || images.length == 0) {
            result.put("error", "No images received");
            return result;
        }

        List<Map<String, Object>> productLabels = new ArrayList<>();

        for (MultipartFile image : images) {

            byte[] imageBytes = new byte[0];
            try {
                imageBytes = image.getBytes();
            } catch (Exception e) {
                result.put("error", "Error reading image file");
                return result;
            }

            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            String body = "{ \"inputs\": [ { \"data\": { \"image\": { \"base64\": \"" + base64Image + "\" } } } ] }";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Key " + clarifaiApiKey);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    CLARIFAI_API_URL,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            if (responseBody == null) {
                result.put("error", "No response from Clarifai");
                return result;
            }

            List<Map<String, Object>> outputs = (List<Map<String, Object>>) responseBody.get("outputs");
            if (outputs == null || outputs.isEmpty()) {
                result.put("error", "No outputs in Clarifai response");
                return result;
            }

            Map<String, Object> firstOutput = outputs.get(0);
            Map<String, Object> data = (Map<String, Object>) firstOutput.get("data");
            if (data == null) {
                result.put("error", "No data in Clarifai output");
                return result;
            }

            List<Map<String, Object>> concepts = (List<Map<String, Object>>) data.get("concepts");
            if (concepts == null) {
                result.put("error", "No concepts detected in image");
                return result;
            }

            for (int i = 0; i < Math.min(concepts.size(), 5); i++) {
                Map<String, Object> concept = concepts.get(i);
                String label = (String) concept.get("name");
                Double score = (Double) concept.get("value");

                Map<String, Object> labelWithScore = new HashMap<>();
                labelWithScore.put("label", label);
                labelWithScore.put("score", score);

                if (productLabels.stream().noneMatch(m -> m.get("label").equals(label))) {
                    productLabels.add(labelWithScore);
                }
            }
        }

        // 🔥 Tri décroissant par score
        productLabels.sort((a, b) -> ((Double) b.get("score")).compareTo((Double) a.get("score")));

        // 🔥 Construire liste de labels + score % pour le frontend
        List<String> displayLabels = productLabels.stream()
                .map(m -> m.get("label") + " (" + Math.round((Double) m.get("score") * 100) + "%)")
                .collect(Collectors.toList());

        result.put("productLabels", displayLabels);

        if (!displayLabels.isEmpty()) {
            result.put("productName", displayLabels.get(0));

            // 🔥 Déduction du type et du prix
            String firstConceptName = displayLabels.get(0).toLowerCase();

            if (firstConceptName.contains("phone") || firstConceptName.contains("laptop") || firstConceptName.contains("tablet") || firstConceptName.contains("tv") || firstConceptName.contains("camera")) {
                result.put("type", "PHYSICAL");
                result.put("price", 1000.0);
                result.put("currencyType", "TND");
                result.put("confidence", "Product detected automatically from multiple images.");
            } else if (firstConceptName.contains("software") || firstConceptName.contains("application") || firstConceptName.contains("program") || firstConceptName.contains("app")) {
                result.put("type", "DIGITAL");
                result.put("price", 50.0);
                result.put("currencyType", "TND");
                result.put("confidence", "Digital product detected automatically from multiple images.");
            } else {
                result.put("type", "PHYSICAL");
                result.put("price", 100.0);
                result.put("currencyType", "TND");
                result.put("confidence", "Generic product detected from multiple images. Please verify the details.");
            }

        } else {
            result.put("error", "No recognizable product detected from images.");
        }

        result.put("stock", 10);

        return result;
    }

}



