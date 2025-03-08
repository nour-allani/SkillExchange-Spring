package tn.esprit.skillexchange.Entity.GestionForumPost;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.Emojis;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmojiComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Enumerated(EnumType.STRING)
    private Emojis emoji ;

    @ManyToOne
    private CommentPost commentPost;

}
