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
    private ParticipationCourses participationCourse;

    private int score;
    private int totalQuestions;
    private int correctAnswers;

    @OneToOne(cascade = CascadeType.ALL)
    private Certificat certificat; // Optional, if you want to attach the cert directly
    @OneToOne
    private ParticipationCourses participation;
}

