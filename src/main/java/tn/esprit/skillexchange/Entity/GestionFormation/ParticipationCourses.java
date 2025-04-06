package tn.esprit.skillexchange.Entity.GestionFormation;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParticipationCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int progress;

    private int participant;

    @ManyToOne
    private Courses course;

    @OneToOne
    private Quiz quiz;

}
