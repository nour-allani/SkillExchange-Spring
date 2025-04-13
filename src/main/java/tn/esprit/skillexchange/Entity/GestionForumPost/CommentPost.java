package tn.esprit.skillexchange.Entity.GestionForumPost;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String email;
    @ManyToOne
    @JoinColumn(name = "post_id_post")
    @JsonIgnore
    private Posts post;

    @OneToMany(mappedBy = "commentPost",cascade = CascadeType.ALL)
    private Set<EmojiComments> emojiComments;

}
