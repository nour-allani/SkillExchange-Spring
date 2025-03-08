package tn.esprit.skillexchange.Service.GestionUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionUser.HistoricTransactions;
import tn.esprit.skillexchange.Repository.GestionUser.HistoricTransictionRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;

import java.util.List;

@Service
public class HistoricTransactionsServiceImpl implements IHistoricTransactionsService{

    @Autowired
    private HistoricTransictionRepo historicTransictionRepo;

    @Override
    public List<HistoricTransactions> retrieveAllHistorique() {
        return historicTransictionRepo.findAll();
    }

    @Override
    public HistoricTransactions add(HistoricTransactions historicTransactions) {
        return historicTransictionRepo.save(historicTransactions);
    }

    @Override
    public HistoricTransactions update(HistoricTransactions historicTransactions) {
        return historicTransictionRepo.findById(historicTransactions.getId())
                .map(existing -> historicTransictionRepo.save(historicTransactions))
                .orElseThrow(() -> new RuntimeException("Historique transaction not found"));
    }

    @Override
    public HistoricTransactions retrieveHistoriqueById(Long id) {
        return historicTransictionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Historique transaction not found"));
    }

    @Override
    public void remove(Long id) {
        historicTransictionRepo.deleteById(id);
    }
}
