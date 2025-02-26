package tn.esprit.skillexchange.Entity.GestionQuiz;


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
    private String image;

    @OneToOne
    private ParticipationCourses participationCourse;

    @OneToOne
    private Certificat certificat;

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL)
    private Set<Questions> questions;

}
