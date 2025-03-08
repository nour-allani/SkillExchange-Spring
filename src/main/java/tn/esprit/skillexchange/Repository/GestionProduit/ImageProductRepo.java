package tn.esprit.skillexchange.Repository.GestionProduit;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionProduit.ImageProduct;

public interface ImageProductRepo extends JpaRepository<ImageProduct,Long> {
}
