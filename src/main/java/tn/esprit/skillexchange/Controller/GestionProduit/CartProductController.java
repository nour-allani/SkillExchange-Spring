package tn.esprit.skillexchange.Controller.GestionProduit;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Entity.GestionProduit.CartProduct;
import tn.esprit.skillexchange.Service.GestionProduit.ICartProductService;


import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/cart-products")
public class CartProductController {

    @Autowired
    private ICartProductService cartpS;

    // Récupérer tous les produits du panier
    @GetMapping("/allCartProducts")
    public List<CartProduct> getAllCartProducts() {
        return cartpS.retrieveCartProducts();
    }

    // Récupérer un produit spécifique du panier par ID
    @GetMapping("/{cartPId}")
    public CartProduct getCartProductById(@PathVariable("cartPId") Long cartPId) {
        return cartpS.retrieveCartProductById(cartPId);
    }
   /* @GetMapping("/cart/{cartId}/products")
    public ResponseEntity<List<CartProduct>> getProductsInCart(@PathVariable Long cartId) {
        List<CartProduct> cartProducts = cartpS.getProductsInCart(cartId);

        if (cartProducts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Aucun produit trouvé
        }

        return ResponseEntity.ok(cartProducts); // Retourne la liste des produits du panier
    }*/
   @GetMapping("/cart/{cartId}/products")
   public ResponseEntity<List<CartProduct>> getProductsInCart(@PathVariable Long cartId) {
       List<CartProduct> cartProducts = cartpS.getProductsInCart(cartId);

       return ResponseEntity.ok(cartProducts); // Même si c'est vide, retourne 200 OK
   }


    /*@PostMapping("/add")
    public CartProduct addProductToCart(@RequestParam("cartId") Long cartId,
                                        @RequestParam("productId") Long productId,
                                        @RequestParam("quantity") int quantity) {
        return cartpS.addProductToCart(cartId, productId, quantity);
    }*/
    @PostMapping("/add-to-user-cart")
    public ResponseEntity<CartProduct> addProductToUserCart(@RequestParam Long userId,
                                                            @RequestParam Long productId,
                                                            @RequestParam int quantity) {
        CartProduct addedProduct = cartpS.addProductToCart(userId, productId, quantity);
        if (addedProduct == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // stock insuffisant ou user/product inexistant
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

   /* @PostMapping("/validate-cart")
    public ResponseEntity<String> validateCart(@RequestBody Map<String, Long> payload) {
        Long cartId = payload.get("cartId");

        cartpS.validateCart(cartId);


        return ResponseEntity.ok("✅ Cart validation process completed.");
    }*/
  ///2
   @PostMapping("/validate-cart")
   public ResponseEntity<String> validateCart(@RequestParam Long cartId) {
       cartpS.validateCart(cartId);
       return ResponseEntity.ok("Cart validated successfully");
   }


    @DeleteMapping("/delete")
    public void deleteCartProduct(
            @RequestParam(required = false) Long cartPId,
            @RequestParam(required = false) Long cartId) {

        if (cartPId != null) {
            cartpS.removeCartProduct(cartPId); // Supprimer un produit spécifique
        } else if (cartId != null) {
            cartpS.clearCart(cartId); // Vider tout le panier
        }
    }


   @PatchMapping("/update/{cartPId}")
   public ResponseEntity<?> updateCartProduct(@PathVariable("cartPId") Long cartPId,
                                              @RequestParam("quantity") int quantity) {
       CartProduct updatedCartProduct = cartpS.modifyCartProduct(cartPId, quantity);

       if (updatedCartProduct == null) {
           return ResponseEntity.noContent().build();  // Retourne 204 si le produit a été supprimé
       }

       return ResponseEntity.ok(updatedCartProduct);  // Retourne le produit mis à jour en JSON
   }

}