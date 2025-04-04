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
    private int idReview ;
    private String content ;
    private Date createdAt ;
    private Date updatedAt ;
    private int rating ;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
    /*@ManyToOne  // Changed from @OneToOne as it's more common for reviews to have many-to-one with user
    @JoinColumn(name = "user_id")
    private User user;*/
    private String email;

}
