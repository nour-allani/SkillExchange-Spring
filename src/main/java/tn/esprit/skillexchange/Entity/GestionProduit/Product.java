package tn.esprit.skillexchange.Entity.GestionProduit;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProduct ;
    private String productName ;
    private String type ;
    private float price;
    private int stock;

    @ManyToOne
    private User postedBy;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Cart> cart;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ReviewProduct> reviewProducts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ImageProduct> imageProducts;


}
