package tn.esprit.skillexchange.Entity.GestionFormation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionEvents.ParticipationEvents;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.Date;
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
    private int id;
    @Lob
    private byte[] image;
    private String imageType;
    private String title;
    private String description;
    private float duration;
    private String requiredSkills;
    private int state;
    private Date date_ajout ;
    private float price;
    private int approoved;
    //private int paid;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RatingCourse> ratingCourse;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<CourseComment> courseComments;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ParticipationCourses> participationCourses;

    @ManyToOne
    Category category ;
    @OneToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;
}
