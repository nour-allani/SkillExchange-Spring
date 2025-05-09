package tn.esprit.skillexchange.Entity.GestionQuiz;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String image;



    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore

    private Certificat certificat;

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL)
    private Set<Questions> questions;

}
