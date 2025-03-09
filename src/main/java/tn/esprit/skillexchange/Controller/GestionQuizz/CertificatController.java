package tn.esprit.skillexchange.Controller.GestionQuizz;

import tn.esprit.skillexchange.Entity.GestionQuiz.Certificat;
import tn.esprit.skillexchange.Service.Gestionquizz.ICertificatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/certificats")
public class CertificatController {

    @Autowired
    private ICertificatService certificatService;

    @GetMapping
    public List<Certificat> getAllCertificats() {
        return certificatService.getAllCertificats();
    }

    @GetMapping("/{id}")
    public Optional<Certificat> getCertificatById(@PathVariable Long id) {
        return certificatService.getCertificatById(id);
    }

    @PostMapping
    public Certificat createCertificat(@RequestBody Certificat certificat) {
        return certificatService.createCertificat(certificat);
    }

    @PutMapping("/{id}")
    public Certificat updateCertificat(@PathVariable Long id, @RequestBody Certificat certificat) {
        return certificatService.updateCertificat(id, certificat);
    }

    @DeleteMapping("/{id}")
    public void deleteCertificat(@PathVariable Long id) {
        certificatService.deleteCertificat(id);
    }
}