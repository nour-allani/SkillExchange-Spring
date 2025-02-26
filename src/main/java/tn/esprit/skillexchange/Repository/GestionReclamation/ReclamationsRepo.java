package tn.esprit.skillexchange.Repository.GestionReclamation;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionReclamation.Reclamations;

public interface ReclamationsRepo extends JpaRepository<Reclamations,Long> {
}
