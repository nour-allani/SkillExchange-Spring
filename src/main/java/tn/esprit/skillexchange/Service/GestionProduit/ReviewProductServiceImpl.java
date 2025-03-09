package tn.esprit.skillexchange.Service.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;
import tn.esprit.skillexchange.Repository.GestionProduit.ReviewProductRepo;

import java.util.List;
@Service
@AllArgsConstructor
public class ReviewProductServiceImpl implements  IReviewProductService{
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
    public ReviewProduct modifyReviewProduct(ReviewProduct ReviewProduct) {
        return rpRepo.save(ReviewProduct);
    }
}
