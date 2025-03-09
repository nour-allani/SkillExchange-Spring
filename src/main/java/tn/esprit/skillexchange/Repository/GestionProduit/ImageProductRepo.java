package tn.esprit.skillexchange.Repository.GestionProduit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionProduit.ImageProduct;
@Repository
public interface ImageProductRepo extends JpaRepository<ImageProduct,Long> {
}
