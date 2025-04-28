package tn.esprit.skillexchange.Repository.GestionProduit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionProduit.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {
}
