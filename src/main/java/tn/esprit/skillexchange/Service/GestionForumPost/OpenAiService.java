package tn.esprit.skillexchange.Service.GestionForumPost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OpenAiService {
    @Value("${openai.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public OpenAiService(WebClient.Builder webClientBuilder) {
        //this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/chat/completions").build();
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/chat/completions").build();

    }

    public Mono<String> generateComment(String postContent) {
        OpenAiRequest request = new OpenAiRequest("gpt-3.5-turbo", postContent);
        return this.webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OpenAiResponse.class)
                .map(response -> {
                    if (response.getChoices() != null && !response.getChoices().isEmpty()) {
                        return response.getChoices().get(0).getText().trim();
                    }
                    return "Sorry, I couldn't generate a comment.";
                });
    }



    @Data
    public static class OpenAiRequest {
        private String model;
        private String prompt;
        @JsonProperty("max_tokens")
        private int maxTokens = 100;
        private double temperature = 0.7;

        public OpenAiRequest(String model, String prompt) {
            this.model = model;
            this.prompt = prompt;
        }
    }

    @Data
    public static class OpenAiResponse {
        private List<Choice> choices;

        @Data
        public static class Choice {
            private String text;
        }
    }
}
