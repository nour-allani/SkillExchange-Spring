package tn.esprit.skillexchange.Entity.GestionForumPost;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPost ;
    private String content ;
    private String ImageP ;
    private String title ;
    private Date createdAt ;
    private Date updatedAt ;
    private int upVote;
    private int downVote;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<EmojiPosts> emojiPosts;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<CommentPost> commentPosts;


}
