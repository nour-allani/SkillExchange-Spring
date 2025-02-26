package tn.esprit.skillexchange.Entity.GestionProduit;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ReviewProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReview ;
    private String content ;
    private Date createdAt ;
    private Date updatedAt ;
    private int rating ;

    @ManyToOne
    private Product product;
}
