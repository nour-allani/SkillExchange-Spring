package tn.esprit.skillexchange.Service.GestionUser;

import tn.esprit.skillexchange.Entity.GestionUser.Badge;
import tn.esprit.skillexchange.Entity.GestionUser.Banned;

import java.util.List;

public interface IBadgeService {
    List<Badge> retrieveBadges();
    Badge add(Badge banned);
    Badge update(Badge banned);
    Badge retrieveBadgeById(Long id);
    void remove(Long id);
}
