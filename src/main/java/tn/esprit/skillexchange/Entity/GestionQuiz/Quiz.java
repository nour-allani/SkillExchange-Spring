package tn.esprit.skillexchange.Entity.GestionQuiz;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String image;



    @OneToOne
    @JsonIgnore
    private Certificat certificat;

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL)
    private Set<Questions> questions;

}
