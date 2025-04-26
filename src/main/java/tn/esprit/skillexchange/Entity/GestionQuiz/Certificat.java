package tn.esprit.skillexchange.Entity.GestionQuiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Certificat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @Column(columnDefinition = "LONGTEXT")
    private String verificationUrl;

    private String skill;

    @OneToOne(mappedBy = "certificat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Result result;

    @ManyToOne
    @JoinColumn(name = "signed_by_id")
    private User signedBy;

    @Column(columnDefinition = "LONGTEXT")
    private String qrCode;
    @ManyToOne
    @JoinColumn(name = "participation_id")
    private ParticipationCourses participation;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private String image;

    @Column(columnDefinition = "LONGTEXT")
    private String htmlContent;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String pdfContent;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String jpgContent;


}