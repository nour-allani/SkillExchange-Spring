package tn.esprit.skillexchange.Controller.GestionFormation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionFormation.OllamaResponse;
import tn.esprit.skillexchange.Service.GestionFormation.OllamaService;

@RestController
@RequestMapping("/api/ollama")
public class OllamaController {
    private final OllamaService ollamaService;

    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping("/generate")
    public OllamaResponse generateCourse(@RequestBody String prompt) {
        return ollamaService.generateCourseContent(prompt);
    }

}