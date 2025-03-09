package tn.esprit.skillexchange.Entity.GestionEvents;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EventComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComment;
    private String content;
    private Date date;
    private int idUser;


    @ManyToOne
    private Events event;

}
