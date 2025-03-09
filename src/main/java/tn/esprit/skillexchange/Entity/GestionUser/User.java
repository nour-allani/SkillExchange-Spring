package tn.esprit.skillexchange.Entity.GestionUser;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;
import tn.esprit.skillexchange.Entity.GestionReclamation.Reclamations;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean verified;
    private String image;
    private float balance;
    private String signature;

    @OneToOne
    private Banned ban;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Badge> badge;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<HistoricTransactions> historicTransactions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Reclamations> reclamations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Posts> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Events> events;

    @OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL)
    private Set<Product> products;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Courses> courses;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Category> Categories ;
}
