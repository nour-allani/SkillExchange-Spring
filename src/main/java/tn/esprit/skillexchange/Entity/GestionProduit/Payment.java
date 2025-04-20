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

public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float montant;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datePaiement;

    @Enumerated(EnumType.STRING)
    private PaymentMethod methodePaiement;

    @Enumerated(EnumType.STRING)
    private PaymentStatus statutPaiement;

   /* @Enumerated(EnumType.STRING)
    private ProductType productType;*/
   @ManyToOne

   private Cart cart;

    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    private String userEmail;

    public enum PaymentMethod {
        PAYPAL, VISA, MASTERCARD, STRIPE, BALANCE
    }

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, REFUNDED
    }



}

