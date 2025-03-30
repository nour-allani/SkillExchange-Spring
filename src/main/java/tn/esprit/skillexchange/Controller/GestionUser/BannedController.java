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
}
