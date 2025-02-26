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
public class HistoricTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private float amount;
    private Date date;
    private String source;

    @ManyToOne
    private User user;

}
