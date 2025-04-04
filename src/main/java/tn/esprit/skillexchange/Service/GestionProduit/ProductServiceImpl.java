package tn.esprit.skillexchange.Service.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;
import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;
import tn.esprit.skillexchange.Repository.GestionProduit.ProductRepo;
import tn.esprit.skillexchange.Repository.GestionProduit.ReviewProductRepo;

import java.util.List;
@Service
@AllArgsConstructor
public class ProductServiceImpl implements  IProductService{
  @Autowired
   ProductRepo pRepo;
    @Autowired
    private ReviewProductRepo reviewRepo;
    @Override
    public List<Product> retrieveProducts() {
        return pRepo.findAll();
    }

    @Override
    public Product retrieveProductById(Long ProductId) {
        return pRepo.findById(ProductId).get();
    }

    @Override
    public Product addProduct(Product p) {
        return pRepo.save(p);
    }

    @Override
    public void removeProduct(Long ProductId) {
   pRepo.deleteById(ProductId);
    }

    @Override
    public Product modifyProduct(Product Product) {
        return pRepo.save(Product);
    }


    // Ajouter une revue à un produit sans lancer d'exception si le produit n'est pas trouvé
    public ReviewProduct addReviewToProduct(Long productId, ReviewProduct review) {
        Product product = pRepo.findById(productId).orElse(null); // Retourne null si produit non trouvé

        if (product != null) {
            // Ajouter la revue au produit
            review.setProduct(product);
            return reviewRepo.save(review);
        } else {
            return null;  // Retourner null ou vous pouvez retourner un message ou un objet de type erreur
        }
    }
}
