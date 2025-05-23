package tn.esprit.skillexchange.Entity.GestionUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tn.esprit.skillexchange.Entity.GestionEvents.Events;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Entity.GestionEvents.RateEvent;
import tn.esprit.skillexchange.Entity.GestionFormation.Category;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @Column(columnDefinition = "LONGTEXT")
    private String image;
    private float balance;

    @Column(columnDefinition = "LONGTEXT")
    private String signature;
    private String bio;
    private String facebook;
    private String github;
    private String linkedin;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    private Banned ban;
/// /////////////////////////////////////////////////////////////
// for recommondation events
    @ElementCollection
    private List<String> interests;
/// ////////////////////////////////////////////////////////////
    @ManyToMany
    @JoinTable(
            name = "user_badge",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "badge_id")
    )
    private Set<Badge> badges;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<HistoricTransactions> historicTransactions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Reclamations> reclamations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Posts> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Events> events;

    @OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Product> products;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Courses> courses;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Category> Categories ;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ParticipationEvents> participationEvents; // New relationship

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<RateEvent> rateEvents;


    @Override
    @JsonIgnore

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
