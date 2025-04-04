package tn.esprit.skillexchange.Entity.GestionProduit;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class CartProduct  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnoreProperties({"cartProducts"})

    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("cartProducts") // Empêche la boucle infinie

    private Product product;
}
