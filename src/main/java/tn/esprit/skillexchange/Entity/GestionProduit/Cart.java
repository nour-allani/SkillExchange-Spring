package tn.esprit.skillexchange.Entity.GestionProduit;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Entity.GestionProduit.CartProduct;

import java.util.HashSet;
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

   @ManyToOne
   @JoinColumn(name = "idUser")
   private User user;

    private long idUser;

    @ManyToMany(cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Product> products;
   @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)

    private Set<CartProduct>  cartProducts= new HashSet<>();

}
