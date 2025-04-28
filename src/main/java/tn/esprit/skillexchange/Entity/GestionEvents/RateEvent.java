package tn.esprit.skillexchange.Entity.GestionEvents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class RateEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRate;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private int rating;

    @ManyToOne
    @JsonIgnoreProperties({"rateEvents", "password", "events", "products", "courses", "reclamations", "badges"})
    private User user;

    @ManyToOne
    @JsonIgnoreProperties({"rateEvents", "participationEvents", "eventComments", "images", "user"})
    private Events event;
}

