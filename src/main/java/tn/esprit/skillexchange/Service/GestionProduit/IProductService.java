package tn.esprit.skillexchange.Service.GestionProduit;

import tn.esprit.skillexchange.Entity.GestionProduit.Product;
import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;

import java.util.List;

public interface IProductService {
    public List<Product> retrieveProducts();
    public Product retrieveProductById(Long ProductId);
    public Product addProduct(Product p);
    public void removeProduct(Long ProductId);
    public Product modifyProduct(Product p);
    public ReviewProduct addReviewToProduct(Long productId, ReviewProduct review);
    public void approveProduct(Long productId);
    public void rejectProduct(Long productId);
    List<Product> getAllApprovedProducts();
}
