package tn.esprit.skillexchange.Service.GestionUser;

import tn.esprit.skillexchange.Entity.GestionUser.HistoricTransactions;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.List;

public interface IHistoricTransactionsService {
    List<HistoricTransactions> retrieveAllHistorique();
    HistoricTransactions add(HistoricTransactions historicTransactions);
    HistoricTransactions update(HistoricTransactions historicTransactions);
    HistoricTransactions retrieveHistoriqueById(Long id);
    void remove(Long id);
}
