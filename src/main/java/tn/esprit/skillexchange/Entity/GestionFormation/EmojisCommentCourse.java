package tn.esprit.skillexchange.Entity.GestionFormation;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.Emojis;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmojisCommentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Enumerated(EnumType.STRING)
    private Emojis emoji ;

    @ManyToOne
    private CourseComment courseComment;
}
