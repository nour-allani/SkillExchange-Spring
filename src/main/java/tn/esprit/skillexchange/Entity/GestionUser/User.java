package tn.esprit.skillexchange.Entity.GestionUser;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;
import tn.esprit.skillexchange.Entity.GestionReclamation.Reclamations;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements UserDetails {
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
    private Set<Courses> courses;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
