package tn.esprit.skillexchange.Entity.GestionFormation;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CoursFormation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;

    private String contentType; // video, pdf, word, text, etc.
    private String fileUrl;

    @Lob
    private String textContent; // utilisé uniquement si contentType = text

    @ManyToOne
    Courses course;
}
