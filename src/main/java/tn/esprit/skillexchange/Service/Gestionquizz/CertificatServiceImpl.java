package tn.esprit.skillexchange.Service.Gestionquizz;

import tn.esprit.skillexchange.Entity.GestionQuiz.Certificat;
import tn.esprit.skillexchange.Repository.GestionQuiz.CertificatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CertificatServiceImpl implements ICertificatService {

    @Autowired
    private CertificatRepo certificatRepository;

    public List<Certificat> getAllCertificats() {
        return certificatRepository.findAll();
    }

    public Optional<Certificat> getCertificatById(Long id) {
        return certificatRepository.findById(id);
    }

    public Certificat createCertificat(Certificat certificat) {
        return certificatRepository.save(certificat);
    }

    public Certificat updateCertificat(Long id, Certificat certificat) {
        if (certificatRepository.existsById(id)) {
            certificat.setId(id);
            return certificatRepository.save(certificat);
        }
        return null;
    }

    public void deleteCertificat(Long id) {
        certificatRepository.deleteById(id);
    }
}
