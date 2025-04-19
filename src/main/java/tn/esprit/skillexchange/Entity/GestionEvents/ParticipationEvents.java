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
    private Status status;
    @ManyToOne
    @JsonBackReference
    private Events event;

    @ManyToOne
    @JsonIgnore
    private User user;


}
