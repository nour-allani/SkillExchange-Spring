package tn.esprit.skillexchange.Repository.GestionUser;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionUser.HistoricTransactions;

public interface HistoricTransictionRepo extends JpaRepository<HistoricTransactions,Long> {
}
