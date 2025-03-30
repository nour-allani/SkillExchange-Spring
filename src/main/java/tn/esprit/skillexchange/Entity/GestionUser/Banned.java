package tn.esprit.skillexchange.Entity.GestionUser;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Banned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String reason;
    private Date endDate;

    private long bannedBy;

    @OneToOne
    private User user;
}
