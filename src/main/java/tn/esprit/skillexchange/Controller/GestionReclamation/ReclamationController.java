package tn.esprit.skillexchange.Controller.GestionReclamation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionReclamation.Reclamations;
import tn.esprit.skillexchange.Service.GestionReclamation.IReclamationService;

import java.util.List;

@RestController
@RequestMapping("/reclamation")
public class ReclamationController {

    @Autowired
    private IReclamationService reclamationService;


    @GetMapping
    public List<Reclamations> getAllReclamations() {
        return reclamationService.retrieveReclamationss();
    }

    @PostMapping
    public Reclamations addReclamation(@RequestBody Reclamations reclamations) {
        return reclamationService.add(reclamations);
    }

    @PutMapping
    public Reclamations updateReclamations(@RequestBody Reclamations Reclamations) {
        return reclamationService.update(Reclamations);
    }

    @GetMapping("/{id}")
    public Reclamations getReclamationsById(@PathVariable Long id) {
        return reclamationService.retrieveReclamationsById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReclamations(@PathVariable Long id) {
        reclamationService.remove(id);
    }
}
