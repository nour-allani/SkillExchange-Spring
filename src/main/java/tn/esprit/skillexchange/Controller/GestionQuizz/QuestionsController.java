package tn.esprit.skillexchange.Controller.GestionQuizz;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.esprit.skillexchange.Entity.GestionQuiz.DTO.QuestionRequest;
import tn.esprit.skillexchange.Entity.GestionQuiz.Questions;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import tn.esprit.skillexchange.Service.Gestionquizz.IQuestionsService;
import tn.esprit.skillexchange.Service.Gestionquizz.QuizService;

import java.util.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionsController.class);

    @Autowired
    private IQuestionsService questionsService;

    @Autowired
    private QuizService quizService;

    @PostMapping
    public ResponseEntity<Questions> createQuestion(@RequestBody QuestionRequest questionRequest) {
        Long quizId = questionRequest.getQuizId();
        Quiz quiz = quizService.getQuizById(quizId);

        if (quiz != null) {
            Questions question = questionRequest.getQuestion();
            question.setQuiz(quiz);
            Questions savedQuestion = questionsService.saveQuestion(question);
            return ResponseEntity.ok(savedQuestion);
        } else {
            return ResponseEntity.badRequest().build(); // Quiz not found
        }
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Questions>> getQuestionsByQuizId(@PathVariable Long quizId) {
        List<Questions> questions = questionsService.getQuestionsByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping
    public ResponseEntity<List<Questions>> getAllQuestions() {
        List<Questions> questions = questionsService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Questions> getQuestionById(@PathVariable Long id) {
        Optional<Questions> question = questionsService.getQuestionById(id);
        return question.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Questions> updateQuestion(@PathVariable Long id, @RequestBody Questions questionDetails) {
        Optional<Questions> existingQuestionOptional = questionsService.getQuestionById(id);
        if (existingQuestionOptional.isPresent()) {
            Questions existingQuestion = existingQuestionOptional.get();
            existingQuestion.setQuestion(questionDetails.getQuestion());
            existingQuestion.setReponse(questionDetails.getReponse());
            existingQuestion.setOption1(questionDetails.getOption1());
            existingQuestion.setOption2(questionDetails.getOption2());
            existingQuestion.setOption3(questionDetails.getOption3());
            existingQuestion.setOption4(questionDetails.getOption4());
            Questions updatedQuestion = questionsService.saveQuestion(existingQuestion);
            return ResponseEntity.ok(updatedQuestion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteQuestion(@PathVariable Long id) {
        if (questionsService.getQuestionById(id).isPresent()) {
            questionsService.deleteQuestion(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Question deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hint/{questionId}")
    public ResponseEntity<Map<String, String>> getQuestionHint(@PathVariable Long questionId) {
        Optional<Questions> questionOpt = questionsService.getQuestionById(questionId);

        if (questionOpt.isPresent()) {
            Questions question = questionOpt.get();
            String hint = generateAiHint(
                    question.getQuestion(),
                    question.getReponse(), // Correct answer for validation
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4()
            );

            Map<String, String> response = new HashMap<>();
            response.put("hint", hint);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String generateAiHint(String question, String... options) {
        String prompt = String.format(
                "Provide ONLY a subtle one-sentence hint for this multiple choice question. " +
                        "DO NOT reveal the correct answer directly. " +
                        "DO NOT mention any specific options. " +
                        "DO NOT say 'the answer is' or similar. " +
                        "Just give a general clue to help think about the question. " +
                        "Question: %s. Options: %s",
                question,
                String.join(", ", options)
        );

        String apiKey = "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";
        String model = "mistralai/mistral-7b-instruct";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        headers.add("HTTP-Referer", "yourdomain.com");
        headers.add("X-Title", "Quiz Hint Generator");

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://openrouter.ai/api/v1/chat/completions",
                    request,
                    Map.class
            );

            // Add null checks for the response structure
            if (response.getBody() == null) {
                return generateSmartFallbackHint(question, options);
            }

            Object choicesObj = response.getBody().get("choices");
            if (!(choicesObj instanceof List) || ((List<?>) choicesObj).isEmpty()) {
                return generateSmartFallbackHint(question, options);
            }

            Object firstChoice = ((List<?>) choicesObj).get(0);
            if (!(firstChoice instanceof Map)) {
                return generateSmartFallbackHint(question, options);
            }

            Object messageObj = ((Map<?, ?>) firstChoice).get("message");
            if (!(messageObj instanceof Map)) {
                return generateSmartFallbackHint(question, options);
            }

            Object contentObj = ((Map<?, ?>) messageObj).get("content");
            if (contentObj instanceof String) {
                return (String) contentObj;
            }

            return generateSmartFallbackHint(question, options);

        } catch (Exception e) {
            logger.error("Failed to generate AI hint", e);
            return generateSmartFallbackHint(question, options);
        }
    }
    private String generateSmartFallbackHint(String question, String... options) {
        int longestOptionIndex = 0;
        int negativeKeywordCount = 0;
        String[] negativeKeywords = {"not", "never", "except"};

        for (int i = 0; i < options.length; i++) {
            if (options[i].length() > options[longestOptionIndex].length()) {
                longestOptionIndex = i;
            }
            for (String keyword : negativeKeywords) {
                if (options[i].toLowerCase().contains(keyword)) {
                    negativeKeywordCount++;
                }
            }
        }

        if (negativeKeywordCount > 0) {
            return "Consider options carefully, especially those with negative terms.";
        } else if (question.length() < 40) {
            return "The answer might be the most detailed or specific option.";
        } else {
            return "Focus on the core concepts mentioned in option " + (char) ('A' + longestOptionIndex) + ".";
        }
    }

}