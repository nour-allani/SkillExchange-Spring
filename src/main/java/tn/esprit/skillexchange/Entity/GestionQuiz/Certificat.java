package tn.esprit.skillexchange.Entity.GestionQuiz;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Certificat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Date obtainedDate;
    private String skill;
    private long signedBy;

    @OneToOne
    private Quiz quiz;
}
