package tn.esprit.skillexchange.Entity.GestionFormation;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaiementCoures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int participant;
    private int paid ;

    @ManyToOne
    private Courses course;


}
