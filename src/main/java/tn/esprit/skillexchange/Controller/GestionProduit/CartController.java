package tn.esprit.skillexchange.Controller.GestionProduit;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Service.GestionProduit.ICartService;

import java.util.List;

@RestController
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cart")
public class CartController {
    @Autowired
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
    public void removeCart(@PathVariable("cart-id") Long cartId) {
        cartS.removeCart(cartId);
    }

    // http://localhost:8084/tpfoyer/chambre/modify-chambre
    @PutMapping("/modify-cart")
    @PatchMapping("/modify-cart")
    public Cart modifyCart(@RequestBody Cart c) {
        return cartS.modifyCart(c);

    }
    @PutMapping("/affecterProductToCart/{productId}/{cartId}")
    @ResponseBody
    public  void affecterProductToCart(@PathVariable("productId")long productId,@PathVariable("cartId")long cartId){
        cartS.affecterProductToCart(productId, cartId);
    }


}
