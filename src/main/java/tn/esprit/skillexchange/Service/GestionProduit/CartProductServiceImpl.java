package tn.esprit.skillexchange.Service.GestionProduit;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Entity.GestionProduit.CartProduct;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionProduit.CartProductRepo;
import tn.esprit.skillexchange.Repository.GestionProduit.CartRepo;
import tn.esprit.skillexchange.Repository.GestionProduit.ProductRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;

import java.util.List;
@Service
@AllArgsConstructor
public class CartProductServiceImpl implements  ICartProductService{
    @Autowired
    private CartProductRepo cartProductRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Override
    public List<CartProduct> retrieveCartProducts() {
       return cartProductRepo.findAll();
    }

    @Override
    public CartProduct retrieveCartProductById(Long cartpId) {
        return cartProductRepo.findById(cartpId).orElse(null);
    }


       /* @Override
        public CartProduct addProductToCart(Long cartId, Long productId, int quantity) {
            // Récupérer le panier et le produit sans utiliser d'exception
            Cart cart = cartRepo.findById(cartId).orElse(null);
            if (cart == null) {
                // Vous pouvez logguer l'erreur ici ou retourner un résultat particulier
                return null;
            }
            Product product = productRepo.findById(productId).orElse(null);
            if (product == null) {
                return null;
            }
            // Vérifier que le stock est suffisant
            if (product.getStock() < quantity) {
                return null;
            }
            // Vérifier si le produit est déjà présent dans le panier
            CartProduct existingCartProduct = cartProductRepo.findByCartAndProduct(cart, product);
            if (existingCartProduct != null) {
               // existingCartProduct.setQuantity(existingCartProduct.getQuantity() + quantity);
                int newQuantity = existingCartProduct.getQuantity() + quantity;
                existingCartProduct.setQuantity(newQuantity);
                // Décrémenter le stock du produit
                product.setStock(product.getStock() - quantity);
                return cartProductRepo.save(existingCartProduct);
            }
            // Sinon, créer une nouvelle entrée dans le panier
            CartProduct cp = new CartProduct();
            cp.setCart(cart);
            cp.setProduct(product);
            cp.setQuantity(quantity);
            product.setStock(product.getStock() - quantity);
            return cartProductRepo.save(cp);
        }
*/
     /*  @Override
       public CartProduct addProductToCart(Long cartId, Long productId, int quantity) {
           Cart cart = cartRepo.findById(cartId).orElse(null);
           if (cart == null) return null;

           Product product = productRepo.findById(productId).orElse(null);
           if (product == null) return null;

           // Optionnel : vérifier que le stock est suffisant au moment de l'ajout au panier
           if (product.getStock() < quantity) {
               return null;
           }

           CartProduct existingCartProduct = cartProductRepo.findByCartAndProduct(cart, product);
           if (existingCartProduct != null) {
               int newQuantity = existingCartProduct.getQuantity() + quantity;
               existingCartProduct.setQuantity(newQuantity);
               return cartProductRepo.save(existingCartProduct);
           }

           CartProduct cp = new CartProduct();
           cp.setCart(cart);
           cp.setProduct(product);
           cp.setQuantity(quantity);
           return cartProductRepo.save(cp);
       }*/
       @Override
       public CartProduct addProductToCart(Long userId, Long productId, int quantity) {
           User user = userRepo.findById(userId).orElse(null);
           if (user == null) return null;

           // 🔹 Récupérer ou créer un panier actif pour l'utilisateur
           Cart cart = cartService.getOrCreateActiveCart(user);

           Product product = productRepo.findById(productId).orElse(null);
           if (product == null) return null;

           // 🔸 Vérification du stock
           if (product.getStock() < quantity) return null;

           // 🔄 Vérifie si le produit est déjà dans le panier
           CartProduct existingCartProduct = cartProductRepo.findByCartAndProduct(cart, product);
           if (existingCartProduct != null) {
               int newQuantity = existingCartProduct.getQuantity() + quantity;

               // Vérifie que la quantité combinée ne dépasse pas le stock
               if (newQuantity > product.getStock() + existingCartProduct.getQuantity()) return null;

               existingCartProduct.setQuantity(newQuantity);
               product.setStock(product.getStock() - quantity);
               productRepo.save(product);
               return cartProductRepo.save(existingCartProduct);
           }

           // ➕ Ajouter un nouveau CartProduct
           CartProduct newCartProduct = new CartProduct();
           newCartProduct.setCart(cart);
           newCartProduct.setProduct(product);
           newCartProduct.setQuantity(quantity);
           product.setStock(product.getStock() - quantity);
           productRepo.save(product);
           return cartProductRepo.save(newCartProduct);
       }

    @Override
    public List<CartProduct> getProductsInCart(Long cartId) {
        return cartProductRepo.findByCartId(cartId);
    }


  /*  @Override
    public void removeCartProduct(Long cartPId) {
        CartProduct cartProduct = cartProductRepo.findById(cartPId).orElse(null);
        if (cartProduct == null) {
            return;
        }

        Product product = cartProduct.getProduct();

        // 🔹 Restaurer le stock du produit supprimé
      product.setStock(product.getStock() + cartProduct.getQuantity());
        productRepo.save(product);  // Mettre à jour le stock dans la base

        // 🔹 Supprimer le produit du panier
        cartProductRepo.delete(cartProduct);
    }*/
  @Override
  public void removeCartProduct(Long cartPId) {
      // Trouver le produit dans le panier
      CartProduct cartProduct = cartProductRepo.findById(cartPId).orElse(null);
      if (cartProduct == null) {
          return; // Si le produit n'existe pas, sortir de la méthode
      }

      Product product = cartProduct.getProduct();

      // 🔹 Restaurer le stock du produit supprimé
      product.setStock(product.getStock() + cartProduct.getQuantity()); // Mettre à jour le stock
      productRepo.save(product); // Sauvegarder les changements du produit

      // 🔹 Supprimer le produit du panier
      cartProductRepo.delete(cartProduct);

      // 🔹 Vérifier si le panier est vide
      Cart cart = cartProduct.getCart();
      if (cart.getCartProducts().isEmpty()) {
          // Désactiver le panier si vide
          cart.setActive(false);
          cartRepo.save(cart); // Sauvegarder le panier désactivé
      }
  }


   @Override
   public void clearCart(Long cartId) {
       List<CartProduct> cartProducts = cartProductRepo.findByCartId(cartId);
       if (!cartProducts.isEmpty()) {
           for (CartProduct cartProduct : cartProducts) {
               Product product = cartProduct.getProduct();

               // 🔹 Restaurer le stock sans dépasser le stock initial
              // product.setStock(product.getStock() + cartProduct.getQuantity());

               productRepo.save(product);
           }
           cartProductRepo.deleteAll(cartProducts);
       }
   }


    /*@Override
    public CartProduct modifyCartProduct(Long cartPId, int newQuantity) {
        // Vérifier si le CartProduct existe
        CartProduct cartProduct = cartProductRepo.findById(cartPId).orElse(null);
        if (cartProduct == null) {
            return null;
        }

        // Récupérer le produit concerné
        Product product = cartProduct.getProduct();

        // Si la nouvelle quantité est <= 0, supprimer le produit du panier
        if (newQuantity <= 0) {
            cartProductRepo.delete(cartProduct);
            return null; // Indiquer que le produit a été supprimé
        }

        // Vérifier que la quantité demandée ne dépasse pas le stock disponible
        if (newQuantity > product.getStock()) {
            return null; // Retourner null ou gérer l'erreur autrement
        }

        // Mettre à jour la quantité
        cartProduct.setQuantity(newQuantity);

        return cartProductRepo.save(cartProduct);
    }*/
    @Override
    public CartProduct modifyCartProduct(Long cartPId, int newQuantity) {
        CartProduct cartProduct = cartProductRepo.findById(cartPId).orElse(null);
        if (cartProduct == null) {
            return null;
        }

        Product product = cartProduct.getProduct();
        int oldQuantity = cartProduct.getQuantity();  // Quantité avant modification

        // 🔹 Si suppression du produit, restaurer tout le stock pris
        if (newQuantity <= 0) {
            product.setStock(product.getStock() + oldQuantity);
            productRepo.save(product);
            cartProductRepo.delete(cartProduct);
            return null;
        }

        int difference = newQuantity - oldQuantity;  // 🔄 Différence entre ancienne et nouvelle quantité

        // 🔸 Vérifier que la nouvelle quantité demandée est possible
        if (difference > product.getStock()) {
            return null; // Pas assez de stock pour augmenter
        }

        // 🔹 Mettre à jour le stock et sauvegarder
        product.setStock(product.getStock() - difference);
        productRepo.save(product);

        // 🔹 Mettre à jour la quantité du produit dans le panier
        cartProduct.setQuantity(newQuantity);
        return cartProductRepo.save(cartProduct);
    }
    @Transactional
    @Override
    public void validateCart(Long cartId) {
        Cart cart = cartRepo.findById(cartId).orElse(null);

        if (cart == null) {
            System.err.println("❌ Cart not found for ID: " + cartId);
            return;
        }

        if (!cart.isActive()) {
            System.out.println("⚠️ Cart already validated.");
            return;
        }

        List<CartProduct> cartProducts = cartProductRepo.findByCart(cart);

        for (CartProduct cp : cartProducts) {
            Product product = cp.getProduct();
            int quantityPurchased = cp.getQuantity();

            if (product.getStock() >= quantityPurchased) {
                product.setStock(product.getStock() - quantityPurchased);
                productRepo.save(product);
            } else {
                System.err.println("⚠️ Not enough stock for product: " + product.getProductName());
                // Ne rien faire si pas assez de stock
            }
        }

        cart.setActive(false); // Désactiver le panier
        cartRepo.save(cart);

        System.out.println("✅ Cart validated successfully.");
    }

}

