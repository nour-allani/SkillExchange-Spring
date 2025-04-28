package tn.esprit.skillexchange.Entity.GestionEvents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImage;

    @Column(columnDefinition = "LONGTEXT")
    private String images;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private Events event;
}