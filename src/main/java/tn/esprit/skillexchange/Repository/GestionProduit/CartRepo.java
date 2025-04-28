package tn.esprit.skillexchange.Repository.GestionProduit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Entity.GestionUser.User;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
 //   Cart findByUser(User user);
    Cart findByUserAndIsActiveTrue(User user);

}
