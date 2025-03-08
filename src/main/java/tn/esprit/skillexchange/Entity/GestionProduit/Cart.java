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
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantity;


    private long idUser;

    @ManyToMany(cascade=CascadeType.ALL)
    private Set<Product> products;
}
