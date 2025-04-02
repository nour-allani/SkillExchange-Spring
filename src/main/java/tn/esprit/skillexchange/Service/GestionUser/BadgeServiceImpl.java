package tn.esprit.skillexchange.Service.GestionUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionUser.Badge;
import tn.esprit.skillexchange.Entity.GestionUser.Role;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionUser.BadgeRepo;
import tn.esprit.skillexchange.Repository.GestionUser.HistoricTransictionRepo;

import java.util.List;
import java.util.Map;

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

    @Override
    public Badge updateBadgePartially(Long id, Map<String, Object> updates) {
        Badge badge = badgeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Badge not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "description":
                    badge.setDescription((String) value);
                    break;
                case "title":
                    badge.setTitle((String) value);
                    break;
                case "image":
                    badge.setImage((String) value);
                    break;
                default:
                    break;
            }
        });

        return badgeRepo.save(badge);
    }
}
