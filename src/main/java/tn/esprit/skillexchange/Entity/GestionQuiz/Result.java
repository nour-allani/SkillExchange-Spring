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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "participation_id")
    private ParticipationCourses participation;

    private int score;
    private int totalQuestions;
    private int correctAnswers;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "certificat_id")
    private Certificat certificat;

}

