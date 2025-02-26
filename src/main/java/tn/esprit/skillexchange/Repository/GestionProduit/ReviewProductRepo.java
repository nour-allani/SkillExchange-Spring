package tn.esprit.skillexchange.Repository.GestionProduit;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;

public interface ReviewProductRepo extends JpaRepository<ReviewProduct,Long> {
}
