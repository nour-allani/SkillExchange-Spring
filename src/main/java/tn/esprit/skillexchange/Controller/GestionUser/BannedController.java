package tn.esprit.skillexchange.Controller.GestionUser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionUser.Banned;
import tn.esprit.skillexchange.Service.GestionUser.IBannedService;

import java.util.List;

@RestController
@RequestMapping("/banned")
public class BannedController {

    @Autowired
    private IBannedService bannedService;


    @GetMapping
    public List<Banned> getAll() {
        return bannedService.retrieveAllBanned();
    }

    @PostMapping
    public Banned add(@RequestBody Banned banned) {
        return bannedService.add(banned);
    }

    @PutMapping
    public Banned update(@RequestBody Banned banned) {
        return bannedService.update(banned);
    }

    @GetMapping("/{id}")
    public Banned getBannedById(@PathVariable Long id) {
        return bannedService.retrieveBannedById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bannedService.remove(id);
    }
}
