package tn.esprit.skillexchange.Service.GestionReclamation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionReclamation.ReclamationReply;
import tn.esprit.skillexchange.Entity.GestionReclamation.ReclamationReply;
import tn.esprit.skillexchange.Repository.GestionReclamation.ReclamationReplyRepo;

import java.util.List;

@Service
public class ReclamationReplyServiceImpl implements IReclamationReplyService {

    @Autowired
    private ReclamationReplyRepo ReclamationReplyRepo;

    @Override
    public List<ReclamationReply> retrieveReclamationReplys() {
        return ReclamationReplyRepo.findAll();
    }

    @Override
    public ReclamationReply add(ReclamationReply ReclamationReply) {
        return ReclamationReplyRepo.save(ReclamationReply);
    }

    @Override
    public ReclamationReply update(ReclamationReply ReclamationReply) {
        return ReclamationReplyRepo.findById(ReclamationReply.getIdReply())
                .map(existing -> ReclamationReplyRepo.save(ReclamationReply))
                .orElseThrow(() -> new RuntimeException("ReclamationReply not found"));
    }

    @Override
    public ReclamationReply retrieveReclamationReplyById(Long id) {
        return ReclamationReplyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ReclamationReply not found"));
    }

    @Override
    public void remove(Long id) {
        ReclamationReplyRepo.deleteById(id);
    }
}
