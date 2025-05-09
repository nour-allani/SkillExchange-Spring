package tn.esprit.skillexchange.Entity.GestionFormation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CourseComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long author;
    private String content;
    private Date date;

    @ManyToOne
    private Courses course;

    @OneToMany(mappedBy = "courseComment",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<EmojisCommentCourse> emojisCommentCourse;

}
