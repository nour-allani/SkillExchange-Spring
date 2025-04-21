package tn.esprit.skillexchange.Entity.GestionForumPost;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ImagePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(columnDefinition = "LONGTEXT")
    private String Image ;

    @ManyToOne
    @JsonIgnore
    private Posts post;
}
