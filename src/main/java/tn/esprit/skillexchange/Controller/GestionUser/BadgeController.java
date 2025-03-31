package tn.esprit.skillexchange.Controller.GestionUser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionUser.Badge;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Service.GestionUser.IBadgeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/badges")
public class BadgeController {

    @Autowired
    private IBadgeService badgeService;


    @GetMapping
    public List<Badge> getAllBadges() {
        return badgeService.retrieveBadges();
    }

    @PostMapping
    public Badge addBadge(@RequestBody Badge badge) {
        return badgeService.add(badge);
    }

    @PutMapping
    public Badge updateBadge(@RequestBody Badge badge) {
        return badgeService.update(badge);
    }

    @GetMapping("/{id}")
    public Badge getBadgeById(@PathVariable Long id) {
        return badgeService.retrieveBadgeById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBadge(@PathVariable Long id) {
        badgeService.remove(id);
    }


    @PatchMapping("/{id}")
    public Badge updateBadgePartially(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return badgeService.updateBadgePartially(id, updates);
    }
}
