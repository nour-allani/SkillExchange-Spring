package tn.esprit.skillexchange.Entity.GestionForumPost;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
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
    private Long idComment;  // Utilisation de Long pour l'ID (plus flexible que int)

    private String content;  // Contenu du commentaire

    private Date createdAt;  // Date de création du commentaire

    private Date updatedAt;  // Date de mise à jour du commentaire

    private String email;  // Email de l'utilisateur ayant écrit le commentaire

    @ManyToOne
    @JoinColumn(name = "post_id_post")
    @JsonIgnore  // Empêche la sérialisation de l'objet 'Post' pour éviter la récursion infinie
    private Posts post;  // Le post auquel appartient ce commentaire

    @OneToMany(mappedBy = "commentPost", cascade = CascadeType.ALL)
   // private Set<EmojiComments> emojiComments;  // Liste des réactions d'emojis liées à ce commentaire
    private Set<EmojiComments> emojiComments = new HashSet<>();  // Initialisation à une collection vide


    @PrePersist
    protected void onCreate() {
        createdAt = new Date();  // Initialisation de la date de création
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();  // Mise à jour de la date de mise à jour
    }
}
