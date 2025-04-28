package tn.esprit.skillexchange.Entity.GestionQuiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String reponse;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
  
    @ManyToOne
    @JsonIgnore
    private Quiz quiz;


}
