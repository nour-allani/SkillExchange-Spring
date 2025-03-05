package tn.esprit.skillexchange.Service.GestionUser;

import tn.esprit.skillexchange.Entity.GestionUser.Banned;
import tn.esprit.skillexchange.Entity.GestionUser.HistoricTransactions;

import java.util.List;

public interface IBannedService {
    List<Banned> retrieveAllBanned();
    Banned add(Banned banned);
    Banned update(Banned banned);
    Banned retrieveBannedById(Long id);
    void remove(Long id);
}
