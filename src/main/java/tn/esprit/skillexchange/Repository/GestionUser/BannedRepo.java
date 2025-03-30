package tn.esprit.skillexchange.Repository.GestionUser;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionUser.Banned;

import java.util.Date;
import java.util.List;

public interface BannedRepo extends JpaRepository<Banned,Long> {
    List<Banned> findByEndDateBefore(Date date);
}
