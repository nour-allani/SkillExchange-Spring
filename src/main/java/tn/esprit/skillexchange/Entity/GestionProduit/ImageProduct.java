package tn.esprit.skillexchange.Entity.GestionProduit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ImageProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImage ;

    @Column(columnDefinition = "LONGTEXT")
    private String Image ;

    @ManyToOne
    @JsonIgnore
    private Product product;
}
