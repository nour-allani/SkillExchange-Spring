package tn.esprit.skillexchange.Controller.GestionProduit;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Entity.GestionProduit.CartProduct;
import tn.esprit.skillexchange.Service.GestionProduit.ICartProductService;


import java.util.List;

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
    @GetMapping("/cart/{cartId}/products")
    public List<CartProduct> getCartProducts(@PathVariable Long cartId) {
        return cartpS.getProductsInCart(cartId);
    }


    @PostMapping("/add")
    public CartProduct addProductToCart(@RequestParam("cartId") Long cartId,
                                        @RequestParam("productId") Long productId,
                                        @RequestParam("quantity") int quantity) {
        return cartpS.addProductToCart(cartId, productId, quantity);
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