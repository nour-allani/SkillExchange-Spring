package tn.esprit.skillexchange.Service.GestionUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionUser.Banned;
import tn.esprit.skillexchange.Repository.GestionUser.BannedRepo;
import tn.esprit.skillexchange.Repository.GestionUser.HistoricTransictionRepo;

import java.util.List;

@Service
public class BannedServiceImpl implements IBannedService{

    @Autowired
    private BannedRepo bannedRepo;


    @Override
    public List<Banned> retrieveAllBanned() {
        return bannedRepo.findAll();
    }

    @Override
    public Banned add(Banned banned) {
        return bannedRepo.save(banned);
    }

    @Override
    public Banned update(Banned banned) {
        return bannedRepo.findById(banned.getId())
                .map(existing -> bannedRepo.save(banned))
                .orElseThrow(() -> new RuntimeException("Banned not found"));
    }

    @Override
    public Banned retrieveBannedById(Long id) {
        return bannedRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Banned not found"));
    }

    @Override
    public void remove(Long id) {
        bannedRepo.deleteById(id);
    }
}
