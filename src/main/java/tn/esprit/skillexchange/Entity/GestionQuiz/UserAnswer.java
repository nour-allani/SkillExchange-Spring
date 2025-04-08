package tn.esprit.skillexchange.Entity.GestionQuiz;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private ParticipationCourses participationCourse; // Link to ParticipationCourses (user participation in course)

    @ManyToOne
    private Quiz quiz; // The quiz related to this answer

    @ManyToOne
    private Questions question; // The question that the user answered

    private String userAnswer; // The answer given by the user
    private boolean isCorrect; // Whether the user's answer is correct or not
}
