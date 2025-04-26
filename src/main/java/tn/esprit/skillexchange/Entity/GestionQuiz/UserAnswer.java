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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "participation_id")
    private ParticipationCourses participation;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Questions question;

    private String userAnswer;
    @Column(nullable = false)
    private Boolean isCorrect = false;

    public void setQuiz(Quiz quizById) {
    }
}
