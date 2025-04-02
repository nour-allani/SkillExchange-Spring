package tn.esprit.skillexchange.Repository.GestionProduit;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Entity.GestionProduit.CartProduct;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;

import java.util.List;

@Repository
public interface CartProductRepo  extends JpaRepository<CartProduct,Long> {
    CartProduct findByCartAndProduct(Cart cart, Product product);
    List<CartProduct> findByCartId(Long cartId);


}
