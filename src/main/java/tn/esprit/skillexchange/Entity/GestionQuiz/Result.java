package tn.esprit.skillexchange.Entity.GestionQuiz;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private ParticipationCourses participationCourse; // Link to the ParticipationCourses

    private int score; // The total score the user achieved in the quiz
    private int totalQuestions; // Total number of questions in the quiz
    private int correctAnswers; // Number of correct answers
}
