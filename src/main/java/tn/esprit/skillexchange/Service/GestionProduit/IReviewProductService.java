package tn.esprit.skillexchange.Service.GestionProduit;



import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;

import java.util.List;

public interface IReviewProductService {
    public List<ReviewProduct> retrieveReviewProducts();
    public ReviewProduct retrieveReviewProductById(Long ReviewProductId);
    public ReviewProduct addReviewProduct(ReviewProduct rev);
    public void removeReviewProduct(Long ReviewProductId);
    public ReviewProduct modifyReviewProduct(ReviewProduct ReviewProduct);
}
