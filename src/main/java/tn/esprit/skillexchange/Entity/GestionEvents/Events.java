package tn.esprit.skillexchange.Entity.GestionEvents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;
    private String eventName ;
    private String description;
    private Date startDate;
    private Date endDate;
    private String place;
    private int nbr_max;

    private Double latitude; // Nouvelle propriété pour la latitude
    private Double longitude; // Nouvelle propriété pour la longitude

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ParticipationEvents> participationEvents;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<RateEvent> rateEvents;


    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private Set<EventComment> eventComments;

    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private Set<EventImage> images;

}

