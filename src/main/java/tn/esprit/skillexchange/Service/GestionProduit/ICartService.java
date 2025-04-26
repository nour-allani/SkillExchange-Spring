package tn.esprit.skillexchange.Service.GestionProduit;

import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.List;

public interface ICartService {
    public List<Cart> retrieveCarts();
    public Cart retrieveCartById(Long cartId);
    public Cart getOrCreateActiveCart(User user);
    public Cart addCart(Cart c);
    public void removeCart(Long CartId);
    public Cart modifyCart(Cart Cart);
    public void deactivateCartIfNeeded(Cart cart);
    //public Cart affecterProductToCart(long cartId , long productId);

}
