package tn.esprit.skillexchange.Entity.GestionEvents;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionUser.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParticipationEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idparticipant;



    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status;


    @ManyToOne
    @JsonBackReference
    private Events event;

    @ManyToOne
    @JsonIgnore
    private User user;


    @Override
    public String toString() {
        return "ParticipationEvents{" +
                "idparticipant=" + idparticipant +
                ", status=" + status +
                ", eventId=" + (event != null ? event.getIdEvent() : null) +
                ", userEmail=" + (user != null ? user.getEmail() : null) +
                '}';
    }
}


