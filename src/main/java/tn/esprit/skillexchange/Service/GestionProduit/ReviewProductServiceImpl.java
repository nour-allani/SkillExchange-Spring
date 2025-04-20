package tn.esprit.skillexchange.Service.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;
import tn.esprit.skillexchange.Repository.GestionProduit.ReviewProductRepo;

import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class ReviewProductServiceImpl implements  IReviewProductService{
    @Autowired
    ReviewProductRepo rpRepo;
    @Override
    public List<ReviewProduct> retrieveReviewProducts() {
        return rpRepo.findAll();
    }

    @Override
    public ReviewProduct retrieveReviewProductById(Long ReviewProductId) {
        return rpRepo.findById(ReviewProductId).get();
    }

    @Override
    public ReviewProduct addReviewProduct(ReviewProduct rev) {
        return rpRepo.save(rev);
    }

    @Override
    public void removeReviewProduct(Long ReviewProductId) {
  rpRepo.deleteById(ReviewProductId);
    }



    @Override
    public ReviewProduct modifyReviewProduct(ReviewProduct reviewProduct) {

        ReviewProduct existingReview = rpRepo.findById(reviewProduct.getIdReview()).orElse(null);

        if (existingReview == null) {

            return null;
        }

        // Mise à jour des informations de la revue
        if (reviewProduct.getContent() != null) {
            existingReview.setContent(reviewProduct.getContent());
        }

        if (reviewProduct.getRating()  != 0) {
            existingReview.setRating(reviewProduct.getRating());
        }

        existingReview.setUpdatedAt(new Date());


        return rpRepo.save(existingReview);
    }


    @Override
    public List<ReviewProduct> retrieveReviewsProductById(Long productId) {
        return rpRepo.findByProduct_IdProduct(productId);
    }

}
