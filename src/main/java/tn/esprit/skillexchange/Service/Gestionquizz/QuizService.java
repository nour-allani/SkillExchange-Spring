package tn.esprit.skillexchange.Service.Gestionquizz;

import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import java.util.List;

public interface QuizService {
    // Create a new quiz with the title and base64 image string
    Quiz createQuiz(String title, String base64Image);

    // Retrieve a quiz by its ID
    Quiz getQuizById(Long id);

    // Retrieve all quizzes
    List<Quiz> getAllQuizzes();

    // Update an existing quiz's title and base64 image string
    Quiz updateQuiz(Long id, String title, String base64Image);

    // Delete a quiz by its ID
    void deleteQuiz(Long id);

    // Update the image for an existing quiz (only image)
    Quiz updateQuizImage(Long id, String base64Image);
}
