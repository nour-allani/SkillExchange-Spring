package tn.esprit.skillexchange.Controller.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.skillexchange.Entity.GestionProduit.ImageProduct;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;
import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.GestionProduit.IProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@AllArgsConstructor

@RequestMapping("/product")

public class ProductController {
    @Autowired
    private IProductService pS;
    @Autowired
    private UserRepo userRepo;


    @GetMapping("/retrieve-products")
    public List<Product> getProducts(){
        return pS.retrieveProducts();

    }
    @GetMapping("/retrieve-products/{product-id}")
    public Product retrieveProduct(@PathVariable("product-id")Long pId){
        return pS.retrieveProductById(pId);

    }
    /*@PostMapping("/add-product")
    public Product addProduct(@RequestBody Product p) {
        return pS.addProduct(p);

    }*/
    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveProduct(@PathVariable("id") Long id) {
        pS.approveProduct(id);
        return ResponseEntity.ok("✅ Product approved and email sent.");
    }
    @PutMapping("/reject/{id}")
    public ResponseEntity<String> rejectProduct(@PathVariable("id") Long id) {
        pS.rejectProduct(id);
        return ResponseEntity.ok("✅ Product approved and email sent.");
    }
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {

        User existingUser = userRepo.findById(product.getPostedBy().getId()).orElse(null);

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        product.setPostedBy(existingUser); // Associer un vrai utilisateur persisté

        Product addedProduct = pS.addProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
       Product p = pS.retrieveProductById(id);

       if (p != null) {
           pS.removeProduct(id);
           return ResponseEntity.noContent().build();
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
   }

   @PatchMapping("/modify-product/{id}")
    public Product modifyImageProduct(@RequestBody Product p) {
       if (p.getImageProducts() != null) {
           for (ImageProduct img : p.getImageProducts()) {
               img.setProduct(p); // Ensure images are linked during update
           }
       }
        return pS.modifyProduct(p);

    }
  /*@PatchMapping("/update/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
      Product existing = pS.retrieveProductById(id);

      if (existing != null) {
          existing.setProductName(updatedProduct.getProductName());
          existing.setType(updatedProduct.getType());
          existing.setPrice(updatedProduct.getPrice());
          existing.setStock(updatedProduct.getStock());
          existing.setImageProducts(updatedProduct.getImageProducts());

          Product saved = pS.addProduct(existing); // ou save(existing)
          return ResponseEntity.ok(saved);
      } else {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
  }
*/

    @PostMapping("/{productId}/reviews")
    public ReviewProduct addProductReview(@PathVariable Long productId, @RequestBody ReviewProduct review) {
        return pS.addReviewToProduct(productId, review);
    }

}
