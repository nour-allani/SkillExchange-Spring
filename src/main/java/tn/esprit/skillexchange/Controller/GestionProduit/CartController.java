package tn.esprit.skillexchange.Controller.GestionProduit;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.GestionProduit.ICartService;

import java.util.List;

@RestController
@AllArgsConstructor

@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService cartS;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/retrieve-carts")
    public List<Cart> getCarts(){
        return cartS.retrieveCarts();

    }
    @GetMapping("/retrieveCarts/{cart-id}")
    public Cart retrieveCart(@PathVariable("cart-id")Long cartId){
        return cartS.retrieveCartById(cartId);
    }
    /*@PostMapping("/add-cart")
    public Cart addCart(@RequestBody Cart c) {
        return cartS.addCart(c);
    }
*/
    @GetMapping("/user-cart/{userId}")
    public ResponseEntity<Cart> getOrCreateCart(@PathVariable Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cart cart = cartS.getOrCreateActiveCart(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add-cart")
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {
        User existingUser = userRepo.findById(cart.getUser().getId()).orElse(null);

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        cart.setUser(existingUser); // Associer un utilisateur existant au panier
        Cart addedCart = cartS.addCart(cart);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedCart);
    }

    @DeleteMapping("/remove-cart/{cart-id}")
    public void removeCart(@PathVariable("cart-id") Long cartId) {
        cartS.removeCart(cartId);
    }


    @PatchMapping("/modify-cart")
    public Cart modifyCart(@RequestBody Cart c) {
        return cartS.modifyCart(c);

    }
   /* @PutMapping("/affecterProductToCart/{productId}/{cartId}")
    @ResponseBody
    public  void affecterProductToCart(@PathVariable("productId")long productId,@PathVariable("cartId")long cartId){
        cartS.affecterProductToCart(productId, cartId);
    }*/



}
