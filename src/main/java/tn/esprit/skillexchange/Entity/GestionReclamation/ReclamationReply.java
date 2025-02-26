package tn.esprit.skillexchange.Entity.GestionReclamation;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ReclamationReply {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idReply;

    private Long idAdmin;
    private Date date;
    private String title;
    private String reply;

    @OneToOne
    private Reclamations reclamation;


}

