package tn.esprit.skillexchange.Repository.GestionQuiz;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionQuiz.Certificat;

public interface CertificatRepo extends JpaRepository<Certificat,Long> {
}
