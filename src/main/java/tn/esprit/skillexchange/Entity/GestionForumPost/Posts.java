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
    private Long idPost;

    private String content;
    /*Lob
    private byte[] imageP;
    private String imageType;*/
    private String title;

  //  @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

   // @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private int upVote;
    private int downVote;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private boolean approved = false;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<EmojiPosts> emojiPosts;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<CommentPost> commentPosts;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<ImagePost> imagePost;
}
