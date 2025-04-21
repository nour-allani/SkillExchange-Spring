package tn.esprit.skillexchange.Entity.GestionFormation;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RatingCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idUser;
    private int rating;

    @ManyToOne
    private Courses course;

}

