package tn.esprit.skillexchange.Entity.GestionProduit;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idProduct")

public class Product  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProduct ;
    private String productName ;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    private float price;
    private int stock;
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;
    @Column(name = "is_approved")
    private boolean isApproved = false;
    @JsonProperty("isApproved")
    public boolean getIsApproved() {
        return isApproved;}

    @ManyToOne
    private User postedBy;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)

    @JsonIgnoreProperties("product")

    private Set<CartProduct> cartProducts= new HashSet<>();


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ReviewProduct> reviewProducts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ImageProduct> imageProducts;


}
