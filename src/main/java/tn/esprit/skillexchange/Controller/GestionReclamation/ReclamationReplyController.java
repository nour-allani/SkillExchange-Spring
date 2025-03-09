package tn.esprit.skillexchange.Controller.GestionReclamation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionReclamation.ReclamationReply;
import tn.esprit.skillexchange.Entity.GestionReclamation.Reclamations;
import tn.esprit.skillexchange.Service.GestionReclamation.IReclamationReplyService;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReclamationReplyController {

    @Autowired
    private IReclamationReplyService reclamationReplyService;


    @GetMapping
    public List<ReclamationReply> getAllReclamationReply() {
        return reclamationReplyService.retrieveReclamationReplys();
    }

    @PostMapping
    public ReclamationReply addReclamationReply(@RequestBody ReclamationReply reclamationReply) {
        return reclamationReplyService.add(reclamationReply);
    }

    @PutMapping
    public ReclamationReply updateReclamationReply(@RequestBody ReclamationReply reclamationReply) {
        return reclamationReplyService.update(reclamationReply);
    }

    @GetMapping("/{id}")
    public ReclamationReply getReclamationReplyById(@PathVariable Long id) {
        return reclamationReplyService.retrieveReclamationReplyById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReclamationReply(@PathVariable Long id) {
        reclamationReplyService.remove(id);
    }
}
