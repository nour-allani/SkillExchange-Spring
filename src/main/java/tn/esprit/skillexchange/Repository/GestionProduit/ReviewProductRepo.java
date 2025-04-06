package tn.esprit.skillexchange.Repository.GestionProduit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;

import java.util.List;

@Repository
public interface ReviewProductRepo extends JpaRepository<ReviewProduct,Long> {
    public List<ReviewProduct> findByProduct_IdProduct(Long idProduct);

}
