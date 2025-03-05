package tn.esprit.skillexchange.Service.GestionUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionUser.Badge;
import tn.esprit.skillexchange.Repository.GestionUser.BadgeRepo;
import tn.esprit.skillexchange.Repository.GestionUser.HistoricTransictionRepo;

import java.util.List;

@Service
public class BadgeServiceImpl implements IBadgeService{

    @Autowired
    private BadgeRepo badgeRepo;

    @Override
    public List<Badge> retrieveBadges() {
        return badgeRepo.findAll();
    }

    @Override
    public Badge add(Badge banned) {
        return badgeRepo.save(banned);
    }

    @Override
    public Badge update(Badge banned) {
        return badgeRepo.findById(banned.getId())
                .map(existing -> badgeRepo.save(banned))
                .orElseThrow(() -> new RuntimeException("Badge not found"));
    }

    @Override
    public Badge retrieveBadgeById(Long id) {
        return badgeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Badge not found"));
    }

    @Override
    public void remove(Long id) {
        badgeRepo.deleteById(id);
    }
}
