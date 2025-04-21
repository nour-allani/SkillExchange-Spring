package tn.esprit.skillexchange.Service.GestionFormation;

import tn.esprit.skillexchange.Entity.GestionFormation.PaiementCoures;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;

import java.util.List;

public interface PaiementCourseService {
    public List<PaiementCoures> retrieveAllPaiements();
    public PaiementCoures retrievePaiement(int paiementId);
    public PaiementCoures addPaiement(PaiementCoures p);
    public void removePaiement(int PaiementId);
    public PaiementCoures modifyPaiements(PaiementCoures p);
    public boolean checkPaiment(long userId, int courseId);
}
