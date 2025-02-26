package tn.esprit.skillexchange.Entity.GestionEvents;

import jakarta.persistence.*;
import lombok.*;

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
    private Events event;

}
