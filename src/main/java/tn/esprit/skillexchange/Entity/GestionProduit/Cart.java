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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

   @ManyToOne
   @JoinColumn(name = "idUser")

   private User user;


   @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
   @JsonIgnoreProperties("cart")

    private Set<CartProduct>  cartProducts= new HashSet<>();

}
