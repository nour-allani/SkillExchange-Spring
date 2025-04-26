package tn.esprit.skillexchange.Entity.GestionForumPost;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.Emojis;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmojiComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Emojis emoji;

    @ManyToOne
   // @JoinColumn(name = "comment_post_id")  // Cette colonne fait référence à l'ID de CommentPost
    @JsonIgnore
    private CommentPost commentPost;

    private String email;
    private LocalDateTime reactedAt;
    @ManyToOne
    private User user;


    // Méthode pour associer un CommentPost existant à EmojiComments
    public void setCommentPost(CommentPost commentPost) {
        this.commentPost = commentPost;
    }
}
