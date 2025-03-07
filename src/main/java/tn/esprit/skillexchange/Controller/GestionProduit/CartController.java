package tn.esprit.skillexchange.Controller.GestionProduit;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Service.GestionProduit.ICartService;

import java.util.List;
@RequestMapping("/cart")
@RestController

public class CartController {
    private ICartService cartS;
    @GetMapping("/retrieve-carts")
    public List<Cart> getCarts(){
        return cartS.retrieveCarts();

    }
    @GetMapping("/retrieveCarts/{cart-id}")
    public Cart retrieveCart(@PathVariable("cart-id")Long cartId){
        return cartS.retrieveCartById(cartId);
    }
    @PostMapping("/add-cart")
    public Cart addCart(@RequestBody Cart c) {
        return cartS.addCart(c);
    }
    @DeleteMapping("/remove-cart/{cart-id}")
    public void removeChambre(@PathVariable("cart-id") Long cartId) {
        cartS.removeCart(cartId);
    }
    // http://localhost:8084/tpfoyer/chambre/modify-chambre
    @PutMapping("/modify-cart")
    public Cart modifyCart(@RequestBody Cart c) {
        return cartS.modifyCart(c);

    }


}
