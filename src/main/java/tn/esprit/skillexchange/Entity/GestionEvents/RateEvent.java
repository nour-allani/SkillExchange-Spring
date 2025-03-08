package tn.esprit.skillexchange.Entity.GestionEvents;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RateEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private int rating;

    @ManyToOne
    private Events event;


}
