package tn.esprit.skillexchange.Controller.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import tn.esprit.skillexchange.Entity.GestionProduit.Product;

import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;
import tn.esprit.skillexchange.Repository.GestionProduit.ProductRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.GestionProduit.IReviewProductService;

import java.util.Date;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/reviewProduct")

public class ReviewProductController {
    @Autowired
    private IReviewProductService revpS;
    @Autowired
    private UserRepo u;
    @Autowired
    private ProductRepo  pRepo;



    @GetMapping("/all-Reviews")
    public List<ReviewProduct> getAllReviews() {
        return revpS.retrieveReviewProducts();
    }

    @GetMapping("/retrieve-reviews-by-product/{productId}")
    public List<ReviewProduct> getReviewsByProductId(@PathVariable("productId") Long productId) {
        return revpS.retrieveReviewsProductById(productId);
    }

  @PostMapping("/add-ReviewProduct")
  public ReviewProduct addReviewProduct(@RequestBody ReviewProduct rev,
                                        @RequestParam Long productId) {
      Product product = pRepo.findById(productId).orElse(null);

      if (product == null) {
          // Retourne null ou une Review vide ou un message d'erreur custom si besoin
          return null;
      }

      rev.setProduct(product); // associer le produit
      rev.setCreatedAt(new Date());
      rev.setUpdatedAt(new Date());

      return revpS.addReviewProduct(rev);
  }


    @DeleteMapping("/remove-ReviewProduct/{ReviewProduct-id}")
    public void removeReviewProduct(@PathVariable("ReviewProduct-id") Long revpId) {
        revpS.removeReviewProduct(revpId);
    }


    @PatchMapping("/{id}")
    public ReviewProduct modifyReviewProduct(@PathVariable("id") Long id, @RequestBody ReviewProduct reviewProduct) {
        reviewProduct.setIdReview(id);

        ReviewProduct updatedReview = revpS.modifyReviewProduct(reviewProduct);

   
        return updatedReview;
    }






}


