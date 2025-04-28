package tn.esprit.skillexchange.Service.GestionFormation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tn.esprit.skillexchange.Entity.GestionFormation.OllamaRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseGenerationService {
//    private final WebClient webClient;
//    private final ObjectMapper objectMapper;
//
//    @Value("${youtube.api.key}")
//    private String youtubeApiKey;
//
//    public CourseGenerationService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
//        this.webClient = webClientBuilder.build();
//        this.objectMapper = objectMapper;
//    }
//
//    public Mono<GeneratedCourseDTO> generateCourse(String title) {
//        if (title == null || title.trim().isEmpty()) {
//            return Mono.error(new IllegalArgumentException("Title cannot be empty"));
//        }
//
//        String prompt = """
//            You are an expert course creator. Generate a detailed and complete course based on the title "%s" with the following structure:
//            - "description": A 100-word description of the course (string).
//            - "sections": An array of 3 sections, each with:
//              - "title": A concise section title (string).
//              - "content": 200 words of detailed content (string).
//            - "quizzes": An array of 2 quizzes, each with:
//              - "title": A quiz title (string).
//              - "questions": An array of 4 multiple-choice questions, each with:
//                - "text": The question text (string).
//                - "options": An array of 4 answer options (strings).
//                - "correctAnswer": The correct option (string, matching one of the options).
//            Return the response as a valid JSON object wrapped in ```json\n...\n``` to ensure proper formatting.
//            Ensure all content is educational, accurate, and well-structured.
//            Example:
//            ```json
//            {
//              "description": "This course introduces Python programming...",
//              "sections": [
//                {
//                  "title": "Introduction",
//                  "content": "Python is a versatile language..."
//                },
//                ...
//              ],
//              "quizzes": [
//                {
//                  "title": "Quiz 1",
//                  "questions": [
//                    {
//                      "text": "What is Python?",
//                      "options": ["A snake", "A language", "A tool", "A framework"],
//                      "correctAnswer": "A language"
//                    },
//                    ...
//                  ]
//                },
//                ...
//              ]
//            }
//            ```
//            """.formatted(title);
//
//        return callOllamaApi(prompt)
//                .flatMap(courseContent -> {
//                    try {
//                        // Extract JSON from ```json\n...\n``` delimiters
//                        String jsonContent = courseContent.replace("```json\n", "").replace("\n```", "").trim();
//                        GeneratedCourseDTO course = objectMapper.readValue(jsonContent, GeneratedCourseDTO.class);
//                        course.setTitle(title);
//                        return Mono.just(course);
//                    } catch (Exception e) {
//                        return Mono.error(new RuntimeException("Error parsing course content: " + e.getMessage(), e));
//                    }
//                })
//                .flatMap(course ->
//                        searchYoutubeVideos(title)
//                                .map(youtubeLinks -> {
//                                    course.setYoutubeLinks(youtubeLinks);
//                                    return course;
//                                })
//                );
//    }
//
//    private Mono<String> callOllamaApi(String prompt) {
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model", "mistral"); // Use "llama3" if preferred
//        requestBody.put("prompt", prompt);
//        requestBody.put("stream", false);
//
//        return Mono.fromCallable(() -> objectMapper.writeValueAsString(requestBody))
//                .doOnNext(json -> System.out.println("Ollama Request: " + json))
//                .flatMap(json -> webClient
//                        .post()
//                        .uri("http://localhost:11434/api/generate")
//                        .header("Content-Type", "application/json")
//                        .bodyValue(requestBody)
//                        .retrieve()
//                        .onStatus(status -> status.is4xxClientError(), clientResponse ->
//                                clientResponse.bodyToMono(String.class)
//                                        .flatMap(errorBody ->
//                                                Mono.error(new RuntimeException("Client error: " + clientResponse.statusCode() + " - " + errorBody))))
//                        .bodyToMono(String.class))
//                .doOnNext(response -> System.out.println("Ollama Raw Response: " + response))
//                .flatMap(response -> {
//                    try {
//                        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
//                        String content = (String) responseMap.get("response");
//                        if (content == null) {
//                            return Mono.error(new RuntimeException("No response content from Ollama"));
//                        }
//                        return Mono.just(content);
//                    } catch (Exception e) {
//                        return Mono.error(new RuntimeException("Error parsing Ollama response: " + e.getMessage(), e));
//                    }
//                });
//    }
//
//    private Mono<List<String>> searchYoutubeVideos(String topic) {
//        String youtubeUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=3&q=" + topic + "&type=video&key=" + youtubeApiKey;
//
//        return webClient.get()
//                .uri(youtubeUrl)
//                .retrieve()
//                .bodyToMono(String.class)
//                .map(response -> {
//                    List<String> videoLinks = new ArrayList<>();
//                    try {
//                        Map<String, Object> youtubeData = objectMapper.readValue(response, Map.class);
//                        List<Map<String, Object>> items = (List<Map<String, Object>>) youtubeData.get("items");
//                        for (Map<String, Object> item : items) {
//                            String videoId = (String) ((Map<String, Object>) item.get("id")).get("videoId");
//                            if (videoId != null) {
//                                videoLinks.add("https://www.youtube.com/watch?v=" + videoId);
//                            }
//                        }
//                    } catch (Exception e) {
//                        throw new RuntimeException("Error searching YouTube: " + e.getMessage(), e);
//                    }
//                    return videoLinks;
//                });
//    }
//}

//    private final WebClient webClient;
//    private final ObjectMapper objectMapper;
//
//    @Value("${youtube.api.key}")
//    private String youtubeApiKey;
//
//    @Value("${ollama.host:http://localhost:11435}")
//    private String ollamaHost;
//
//    public CourseGenerationService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
//        this.webClient = webClientBuilder.build();
//        this.objectMapper = objectMapper;
//    }
//
//    public Mono<OllamaRequest> generateCourse(String title) {
//        if (title == null || title.trim().isEmpty()) {
//            return Mono.error(new IllegalArgumentException("Title cannot be empty"));
//        }
//
//        String prompt = """
//            You are an expert course creator with deep expertise in education. Generate a comprehensive course for "%s" with:
//            - "description": A 50-word engaging overview with key learning outcomes.
//            - "sections": 2 sections, each with:
//              - "title": A specific, descriptive title.
//              - "content": 100 words of in-depth content with practical examples.
//            Return valid JSON in ```json\n...\n```. Ensure content is professional, detailed, and educational.
//            Example:
//            ```json
//            {
//              "description": "This course introduces Python programming, covering syntax and basics...",
//              "sections": [
//                {
//                  "title": "Python Basics",
//                  "content": "Python is a versatile language. Learn variables, e.g., `x = 5`..."
//                },
//                ...
//              ]
//            }
//            ```
//            """.formatted(title);
//
//        return callOllamaApi(prompt)
//                .flatMap(courseContent -> {
//                    try {
//                        String jsonContent = courseContent.replace("```json\n", "").replace("\n```", "").trim();
//                        System.out.println("Parsed JSON Content: " + jsonContent);
//                        if (!jsonContent.startsWith("{") || !jsonContent.endsWith("}")) {
//                            return Mono.error(new RuntimeException("Invalid JSON format from Ollama: " + jsonContent));
//                        }
//                        OllamaRequest course = objectMapper.readValue(jsonContent, OllamaRequest.class);
//                        course.setTitle(title);
//                        return Mono.just(course);
//                    } catch (Exception e) {
//                        return Mono.error(new RuntimeException("Error parsing course content: " + e.getMessage(), e));
//                    }
//                })
//                .flatMap(course ->
//                        searchYoutubeVideos(title)
//                                .map(youtubeLinks -> {
//                                    course.setYoutubeLinks(youtubeLinks);
//                                    return course;
//                                })
//                                .onErrorResume(e -> {
//                                    System.out.println("YouTube API Error: " + e.getMessage());
//                                    course.setYoutubeLinks(new ArrayList<>());
//                                    return Mono.just(course);
//                                })
//                );
//    }
//
//    private Mono<String> callOllamaApi(String prompt) {
//        List<String> modelsToTry = List.of("mistral");
//        return tryModels(modelsToTry, prompt, 0);
//    }
//
//    private Mono<String> tryModels(List<String> models, String prompt, int modelIndex) {
//        if (modelIndex >= models.size()) {
//            return Mono.error(new RuntimeException("All models failed: " + models));
//        }
//
//        String currentModel = models.get(modelIndex);
//        System.out.println("Attempting to use model: " + currentModel);
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model", currentModel);
//        requestBody.put("prompt", prompt);
//        requestBody.put("stream", false);
//
//        return Mono.fromCallable(() -> objectMapper.writeValueAsString(requestBody))
//                .doOnNext(json -> System.out.println("Ollama Request: " + json))
//                .flatMap(json -> webClient
//                        .post()
//                        .uri(ollamaHost + "/api/generate")
//                        .header("Content-Type", "application/json")
//                        .bodyValue(requestBody)
//                        .retrieve()
//                        .onStatus(status -> status.is4xxClientError(), clientResponse ->
//                                clientResponse.bodyToMono(String.class)
//                                        .flatMap(errorBody -> {
//                                            if (errorBody.contains("model") && errorBody.contains("not found")) {
//                                                return Mono.error(new ModelNotFoundException("Model not found: " + currentModel));
//                                            }
//                                            return Mono.error(new RuntimeException("Client error: " + clientResponse.statusCode() + " - " + errorBody));
//                                        }))
//                        .bodyToMono(String.class)
//                        .timeout(Duration.ofSeconds(900))  // Increased to 900 seconds
//                        .doOnError(throwable -> System.out.println("Ollama Timeout/Error for model " + currentModel + ": " + throwable.getMessage())))
//                .doOnNext(response -> System.out.println("Ollama Raw Response: " + response))
//                .flatMap(response -> {
//                    try {
//                        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
//                        String content = (String) responseMap.get("response");
//                        if (content == null) {
//                            return Mono.error(new RuntimeException("No response content from Ollama"));
//                        }
//                        return Mono.just(content);
//                    } catch (Exception e) {
//                        return Mono.error(new RuntimeException("Error parsing Ollama response: " + e.getMessage(), e));
//                    }
//                })
//                .onErrorResume(ModelNotFoundException.class, throwable ->
//                        tryModels(models, prompt, modelIndex + 1));
//    }
//
//    private Mono<List<String>> searchYoutubeVideos(String topic) {
//        String youtubeUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=3&q=" + topic + "&type=video&key=" + youtubeApiKey;
//
//        return webClient.get()
//                .uri(youtubeUrl)
//                .retrieve()
//                .bodyToMono(String.class)
//                .map(response -> {
//                    List<String> videoLinks = new ArrayList<>();
//                    try {
//                        Map<String, Object> youtubeData = objectMapper.readValue(response, Map.class);
//                        List<Map<String, Object>> items = (List<Map<String, Object>>) youtubeData.get("items");
//                        for (Map<String, Object> item : items) {
//                            String videoId = (String) ((Map<String, Object>) item.get("id")).get("videoId");
//                            if (videoId != null) {
//                                videoLinks.add("https://www.youtube.com/watch?v=" + videoId);
//                            }
//                        }
//                    } catch (Exception e) {
//                        throw new RuntimeException("Error searching YouTube: " + e.getMessage(), e);
//                    }
//                    return videoLinks;
//                });
//    }
}
