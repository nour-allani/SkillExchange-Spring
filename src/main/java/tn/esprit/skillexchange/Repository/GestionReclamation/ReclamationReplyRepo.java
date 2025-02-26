package tn.esprit.skillexchange.Repository.GestionReclamation;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionReclamation.ReclamationReply;

public interface ReclamationReplyRepo extends JpaRepository<ReclamationReply,Long> {
}
