package tn.esprit.skillexchange.Service.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.PaiementCoures;
import tn.esprit.skillexchange.Repository.GestionFormation.PaiementCourseRepo;
import tn.esprit.skillexchange.Repository.GestionFormation.ParticipationCourseRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class PaiementCourseImp implements PaiementCourseService {

    @Autowired
    PaiementCourseRepo paiementCourseRepo ;
    @Override
    public List<PaiementCoures> retrieveAllPaiements() {
        return paiementCourseRepo.findAll();
    }

    @Override
    public PaiementCoures retrievePaiement(int paiementId) {
        return paiementCourseRepo.findById(paiementId).get();
    }

    @Override
    public PaiementCoures addPaiement(PaiementCoures p) {
        return paiementCourseRepo.save(p);
    }

    @Override
    public void removePaiement(int PaiementId) {
        paiementCourseRepo.deleteById(PaiementId);
    }

    @Override
    public PaiementCoures modifyPaiements(PaiementCoures p) {
        return paiementCourseRepo.save(p);
    }

    @Override
    public boolean checkPaiment(long userId, int courseId) {
        return paiementCourseRepo.existsPaiement(userId, courseId);
    }
}
