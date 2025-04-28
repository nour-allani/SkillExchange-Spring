package tn.esprit.skillexchange.Controller.GestionFormation;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Entity.GestionFormation.PaiementCoures;
import tn.esprit.skillexchange.Entity.GestionFormation.PaiementCoures;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Service.GestionFormation.FormationService;
import tn.esprit.skillexchange.Service.GestionFormation.PaiementCourseService;
import tn.esprit.skillexchange.Service.GestionUser.IUserService;
import tn.esprit.skillexchange.Service.Mailing.GmailService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/paiement-course")
public class PaiementCourseRestController {
    @Autowired
    PaiementCourseService paiementCourseService ;
    @Autowired
    IUserService userService ;
    @Autowired
    GmailService gmailService ;
    @Autowired
    FormationService courseService ;

    @GetMapping("/retrieve-all-paiements")
    public List<PaiementCoures> getPaiements() {
        List<PaiementCoures> listPaiement = paiementCourseService.retrieveAllPaiements();
        return listPaiement;
    }

    @GetMapping("/retrieve-paiement/{paiement-id}")
    public PaiementCoures retrievePaiements(@PathVariable("paiement-id") int partId) {
        PaiementCoures paiements = paiementCourseService.retrievePaiement(partId);
        return paiements;
    }

    @PostMapping("/add-paiement")
    public PaiementCoures addParticipation(@RequestBody PaiementCoures p) throws MessagingException, IOException {
        PaiementCoures paiement = paiementCourseService.addPaiement(p);

        User toUser = userService.retrieveUserById((long) p.getParticipant());
        String to = toUser.getEmail();
        String subject = "SkillExchange Transaction";
        String userName = toUser.getName() != null ? toUser.getName() : "Utilisateur";

        Courses course = courseService.retrieveCourse((long) p.getCourse().getId());


        // Lire le template HTML
        try (InputStream templateStream = getClass().getClassLoader()
                .getResourceAsStream("templates/email/transactionEmail.html")) {
            if (templateStream == null) {
                throw new IOException("Template email non trouvé");
            }
            String htmlContent = new String(templateStream.readAllBytes(), StandardCharsets.UTF_8);

            // Remplacer les variables
            htmlContent = htmlContent.replace("${username}", userName)
                    .replace("${amount}", String.valueOf(p.getPaid()))
                    .replace("${titleCourse}", course.getTitle());

            // Envoyer l'email
            gmailService.sendHtmlEmail(to, subject, htmlContent);
        }

        return paiement;
    }

    @DeleteMapping("/remove-paiement/{paiement-id}")
    public void removePaiement(@PathVariable("paiement-id") int partId) {
        paiementCourseService.removePaiement(partId);
    }



    @PutMapping("/modify-participation")
    public PaiementCoures modifyParticipation(@RequestBody PaiementCoures p) {
        PaiementCoures paiement = paiementCourseService.modifyPaiements(p);
        return paiement;
    }

    @GetMapping("/check")
    public boolean checkPaiement(
            @RequestParam int participantId,
            @RequestParam int courseId
    ) {
        return paiementCourseService.checkPaiment(participantId, courseId);
    }

}
