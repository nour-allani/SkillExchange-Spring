package tn.esprit.skillexchange.Service.GestionReclamation;

import tn.esprit.skillexchange.Entity.GestionReclamation.Reclamations;

import java.util.List;

public interface IReclamationService {

    List<Reclamations> retrieveReclamationss();
    Reclamations add(Reclamations Reclamations);
    Reclamations update(Reclamations Reclamations);
    Reclamations retrieveReclamationsById(Long id);
    void remove(Long id);
    
}
