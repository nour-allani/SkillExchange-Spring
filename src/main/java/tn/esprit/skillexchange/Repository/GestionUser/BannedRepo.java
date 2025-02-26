package tn.esprit.skillexchange.Repository.GestionUser;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionUser.Banned;

public interface BannedRepo extends JpaRepository<Banned,Long> {
}
