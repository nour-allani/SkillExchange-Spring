package tn.esprit.skillexchange.Entity.GestionForumPost;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CommentPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComment ;
    private String content ;
    private Date createdAt ;
    private Date updatedAt ;

    @ManyToOne
    private Posts post;

    @OneToMany(mappedBy = "commentPost",cascade = CascadeType.ALL)
    private Set<EmojiComments> emojiComments;

}
