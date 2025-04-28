package tn.esprit.skillexchange.Controller.GestionProduit.ProductIA;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.skillexchange.Service.GestionProduit.ProductIA.AiProductService;
import tn.esprit.skillexchange.Service.GestionProduit.ProductIA.AiReviewService;
import tn.esprit.skillexchange.Service.GestionProduit.ProductIA.IAiProductService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")

public class AiProductController  {

    @Autowired
    private AiProductService aiProductService;

    @PostMapping("/analyze-product")
    public ResponseEntity<Map<String, Object>> analyzeProduct(@RequestParam("images") MultipartFile[] images) {
        if (images == null || images.length == 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Aucune image reçue"));
        }

        Map<String, Object> analyzedData = aiProductService.analyzeImages(images);
        return ResponseEntity.ok(analyzedData);
    }
   /* @Autowired
    private AiReviewService aiReviewService;

    @PostMapping("/analyze-review")
    public ResponseEntity<Map<String, String>> analyzeReview(@RequestBody Map<String, String> body) {
        String comment = body.get("comment");

        // 🔥 Appeler ton service IA pour analyser
        String sentiment = aiReviewService.analyzeReview(comment);

        // 🔥 Retourner sous forme de Map
        Map<String, String> result = new HashMap<>();
        result.put("sentiment", sentiment); // POSITIVE / NEGATIVE / NEUTRAL

        return ResponseEntity.ok(result);
    }*/
}
