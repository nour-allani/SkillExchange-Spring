package tn.esprit.skillexchange.Entity.GestionFormation;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParticipationCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idp;
    private int progress;
    private int participant;
    private Date date_participation;

    @ManyToOne
    private Courses course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}