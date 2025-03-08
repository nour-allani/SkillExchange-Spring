package tn.esprit.skillexchange.Service.Gestionquizz;

import tn.esprit.skillexchange.Entity.GestionQuiz.Certificat;

import java.util.List;
import java.util.Optional;

public interface ICertificatService {
    Certificat createCertificat(Certificat certificat);
    List<Certificat> getAllCertificats();
    Optional<Certificat> getCertificatById(Long id);
    Certificat updateCertificat(Long id, Certificat updatedCertificat);
    void deleteCertificat(Long id);
}