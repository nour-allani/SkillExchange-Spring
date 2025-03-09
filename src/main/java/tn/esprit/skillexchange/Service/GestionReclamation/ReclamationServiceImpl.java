package tn.esprit.skillexchange.Service.GestionReclamation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionReclamation.Reclamations;
import tn.esprit.skillexchange.Repository.GestionReclamation.ReclamationsRepo;

import java.util.List;

@Service
public class ReclamationServiceImpl implements IReclamationService{

    @Autowired
    private ReclamationsRepo ReclamationsRepo;
    
    @Override
    public List<Reclamations> retrieveReclamationss() {
        return ReclamationsRepo.findAll();
    }

    @Override
    public Reclamations add(Reclamations Reclamations) {
        return ReclamationsRepo.save(Reclamations);
    }

    @Override
    public Reclamations update(Reclamations Reclamations) {
        return ReclamationsRepo.findById(Reclamations.getIdReclamation())
                .map(existing -> ReclamationsRepo.save(Reclamations))
                .orElseThrow(() -> new RuntimeException("Reclamations not found"));
    }

    @Override
    public Reclamations retrieveReclamationsById(Long id) {
        return ReclamationsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reclamations not found"));
    }

    @Override
    public void remove(Long id) {
        ReclamationsRepo.deleteById(id);
    }
}
