package tn.esprit.skillexchange.Controller.GestionReclamation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.esprit.skillexchange.Entity.GestionReclamation.Reclamations;
import tn.esprit.skillexchange.Service.GestionReclamation.IReclamationService;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reclamation")
public class ReclamationController {

    @Autowired
    private IReclamationService reclamationService;

    @Value("${whatsapp.instance.id}")
    private String instanceId;

    @Value("${whatsapp.token}")
    private String whatsappToken;

    @Value("${whatsapp.phone.number}")
    private String phoneNumber;


    @GetMapping
    public List<Reclamations> getAllReclamations() {
        return reclamationService.retrieveReclamationss();
    }

    @PostMapping
    public Reclamations addReclamation(@RequestBody Reclamations reclamations) {
        return reclamationService.add(reclamations);
    }

    @PutMapping("/{id}")
    public Reclamations updateReclamations(@PathVariable Long id, @RequestBody Reclamations reclamations) {
        reclamations.setIdReclamation(id);
        return reclamationService.update(reclamations);
    }

    @GetMapping("/{id}")
    public Reclamations getReclamationsById(@PathVariable Long id) {
        return reclamationService.retrieveReclamationsById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReclamations(@PathVariable Long id) {
        reclamationService.remove(id);
    }

    @PostMapping("/send-whatsapp")
    public ResponseEntity<Map<String, String>> sendWhatsApp(@RequestBody String message) {
        Map<String, String> responseBody = new HashMap<>();

        try {
            String url = "https://api.ultramsg.com/" + instanceId + "/messages/chat";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("token", whatsappToken);
            body.add("to", phoneNumber);
            body.add("body", message);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> apiResponse = new RestTemplate().postForEntity(url, request, String.class);

            if (apiResponse.getStatusCode().is2xxSuccessful()) {
                responseBody.put("status", "success");
                responseBody.put("message", "Message sent");
                return ResponseEntity.ok(responseBody);
            }

            responseBody.put("status", "error");
            responseBody.put("message", "WhatsApp API error: " + apiResponse.getBody());
            return ResponseEntity.status(500).body(responseBody);
        } catch (Exception e) {
            responseBody.put("status", "error");
            responseBody.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(responseBody);
        }
    }
}
