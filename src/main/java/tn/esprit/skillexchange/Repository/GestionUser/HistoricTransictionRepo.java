package tn.esprit.skillexchange.Repository.GestionUser;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionUser.HistoricTransactions;

import java.util.List;

public interface HistoricTransictionRepo extends JpaRepository<HistoricTransactions,Long> {
    List<HistoricTransactions> findByUser_IdOrderByDateDesc(Long userId);
}
