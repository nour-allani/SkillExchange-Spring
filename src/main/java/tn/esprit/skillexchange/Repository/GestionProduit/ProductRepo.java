package tn.esprit.skillexchange.Repository.GestionProduit;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;

public interface ProductRepo extends JpaRepository<Product,Long> {
}
