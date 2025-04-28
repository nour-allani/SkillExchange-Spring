package tn.esprit.skillexchange.Entity.GestionFormation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// Requête vers Ollama
public class OllamaRequest {
    private String model;
    private String prompt;
    private boolean stream;

    // Getters et Setters
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }
    public boolean isStream() { return stream; }
    public void setStream(boolean stream) { this.stream = stream; }
}

