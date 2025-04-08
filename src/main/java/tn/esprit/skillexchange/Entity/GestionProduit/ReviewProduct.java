package tn.esprit.skillexchange.Entity.GestionProduit;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

public class ReviewProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReview ;
    private String content ;
    private Date createdAt ;
    private Date updatedAt ;
    private int rating ;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    private String email;

}
