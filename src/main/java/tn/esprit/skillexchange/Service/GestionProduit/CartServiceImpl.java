package tn.esprit.skillexchange.Service.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionProduit.CartRepo;
import tn.esprit.skillexchange.Repository.GestionProduit.ProductRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
 @AllArgsConstructor
public class CartServiceImpl implements ICartService{
     @Autowired
    private CartRepo cartRepo;
    // private ProductRepo productRepo;
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
   public Cart getOrCreateActiveCart(User user) {
       Cart existingCart = cartRepo.findByUserAndIsActiveTrue(user);
       if (existingCart != null) {
           return existingCart;
       }

       Cart newCart = new Cart();
       newCart.setUser(user);
       newCart.setActive(true);
       return cartRepo.save(newCart);
   }
    @Override
    public void deactivateCartIfNeeded(Cart cart) {
        if (cart.getCartProducts().isEmpty()) {
            cart.setActive(false);
            cartRepo.save(cart);
        }
    }



    @Override
    public void removeCart(Long CartId) {
        cartRepo.deleteById(CartId);

    }

    @Override
    public Cart modifyCart(Cart Cart) {
        return cartRepo.save(Cart);
    }
    /* @Override
     public Cart affecterProductToCart(long cartId, long productId) {
         Cart c=cartRepo.findById(cartId).get();
         Product pr=productRepo.findById(productId).get();
         Set<Product> productMisesAjour=new HashSet<>();
         if(c.getProducts()!=null){
             productMisesAjour.addAll(c.getProducts());
         }
         productMisesAjour.add(pr);
         c.setProducts(productMisesAjour);
         cartRepo.save(c);
         return c;
     }*/

 }
