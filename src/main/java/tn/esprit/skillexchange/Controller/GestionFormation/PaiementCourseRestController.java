package tn.esprit.skillexchange.Controller.GestionFormation;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionFormation.PaiementCoures;
import tn.esprit.skillexchange.Entity.GestionFormation.PaiementCoures;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Service.GestionFormation.PaiementCourseService;
import tn.esprit.skillexchange.Service.GestionUser.IUserService;
import tn.esprit.skillexchange.Service.Mailing.GmailService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/paiement-course")
public class PaiementCourseRestController {
    @Autowired
    PaiementCourseService paiementCourseService ;
    IUserService userService ;
    GmailService gmailService ;

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
    public PaiementCoures addParticipation(@RequestBody PaiementCoures p) throws MessagingException {
        PaiementCoures paiement = paiementCourseService.addPaiement(p);


//        User toUser = userService.retrieveUserById((long) p.getParticipant()) ;
//
//        // Send email to the User
//        String to = toUser.getEmail(); // Use the email from the added supervisor
//        String subject = "Transaction effectué ";
//        String body = "Bonjour " + toUser.getName() + ",\n\nVous avez effectué un paiement avec un montant de : " + p.getPaid() +"!"+"\n\nCordialement";
//
//        gmailService.sendSimpleEmail(to, subject, body);
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
