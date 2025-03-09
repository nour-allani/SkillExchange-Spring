package tn.esprit.skillexchange.Service.GestionReclamation;

import tn.esprit.skillexchange.Entity.GestionReclamation.ReclamationReply;

import java.util.List;

public interface IReclamationReplyService {

    List<ReclamationReply> retrieveReclamationReplys();
    ReclamationReply add(ReclamationReply reclamationReply);
    ReclamationReply update(ReclamationReply reclamationReply);
    ReclamationReply retrieveReclamationReplyById(Long id);
    void remove(Long id);
}
