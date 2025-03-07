package tn.esprit.skillexchange.Service.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Repository.GestionProduit.CartRepo;

import java.util.List;
 @Service
 @AllArgsConstructor
public class CartServiceImpl implements ICartService{
    private CartRepo cartRepo;
    @Override
    public List<Cart> retrieveCarts() {
        return cartRepo.findAll();
    }

    @Override
    public Cart retrieveCartById(Long cartId) {
        return cartRepo.findById(cartId).get();
    }

    @Override
    public Cart addCart(Cart c) {
        return cartRepo.save(c);
    }

    @Override
    public void removeCart(Long CartId) {
        cartRepo.deleteById(CartId);

    }

    @Override
    public Cart modifyCart(Cart Cart) {
        return cartRepo.save(Cart);
    }
}
