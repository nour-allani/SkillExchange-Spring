package tn.esprit.skillexchange.Repository.GestionProduit;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;

public interface CartRepo extends JpaRepository<Cart,Long> {
}
