package tn.esprit.skillexchange.Entity.GestionQuiz.DTO;

import tn.esprit.skillexchange.Entity.GestionQuiz.Questions;

public class QuestionRequest {
    private Long quizId;
    private Questions question;

    // Getters and setters
    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
    }
}
