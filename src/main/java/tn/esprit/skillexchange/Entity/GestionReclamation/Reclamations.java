package tn.esprit.skillexchange.Entity.GestionReclamation;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Reclamations {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idReclamation;
    private String Description;
    private String Status;
    private Date date;
    private Long idUser;
    private String reponse;

    @Column(columnDefinition = "LONGTEXT")
    private String image;
    private String title;

    @ManyToOne
    private User user;

    @OneToOne
    private ReclamationReply reclamationReply;

}
