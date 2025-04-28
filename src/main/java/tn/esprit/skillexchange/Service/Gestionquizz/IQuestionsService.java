package tn.esprit.skillexchange.Service.Gestionquizz;

import tn.esprit.skillexchange.Entity.GestionQuiz.Questions;

import java.util.List;
import java.util.Optional;

public interface IQuestionsService {
    Questions saveQuestion(Questions question);  // Create & Update
    List<Questions> getAllQuestions();          // Retrieve all questions
    Optional<Questions> getQuestionById(Long id);  // Retrieve by ID
    void deleteQuestion(Long id);               // Delete question
    List<Questions> getQuestionsByQuizId(Long quizId);
}