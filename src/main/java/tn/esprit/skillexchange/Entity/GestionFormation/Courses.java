package tn.esprit.skillexchange.Entity.GestionFormation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCourse;
    private String image;
    private String title;
    private String description;
    private float duration;
    private String requiredSkills;
    private String state;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private Set<RatingCourse> ratingCourse;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<CourseComment> courseComments;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private Set<ParticipationCourses> participationCourses;

    @ManyToOne
    Category category ;
}
