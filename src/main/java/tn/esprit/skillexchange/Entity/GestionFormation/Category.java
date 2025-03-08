package tn.esprit.skillexchange.Entity.GestionFormation;

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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String image;
    private String name;
    private String description;

    @ManyToOne
    User user ;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private Set<Courses> courses ;


}
