package tn.esprit.skillexchange.Service.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.skillexchange.Entity.GestionProduit.ImageProduct;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;
import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;
import tn.esprit.skillexchange.Repository.GestionProduit.ImageProductRepo;
import tn.esprit.skillexchange.Repository.GestionProduit.ProductRepo;
import tn.esprit.skillexchange.Repository.GestionProduit.ReviewProductRepo;
import tn.esprit.skillexchange.Service.Mailing.GmailService;

import java.util.List;
@Service
@AllArgsConstructor
public class ProductServiceImpl implements  IProductService{
  @Autowired
   ProductRepo pRepo;
    @Autowired
    private ReviewProductRepo reviewRepo;
    @Autowired
    private ImageProductRepo imgRepo;
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
        if (p.getImageProducts() != null) {
            for (ImageProduct img : p.getImageProducts()) {
                img.setProduct(p); // 🔗 Lier l'image au produit
            }
        }
        p.setApproved(false);
        return pRepo.save(p);
    }

    @Override
    public void removeProduct(Long ProductId) {
   pRepo.deleteById(ProductId);
    }

    @Override
    public Product modifyProduct(Product p) {

       /* if (p.getImageProducts() != null) {
            for (ImageProduct img : p.getImageProducts()) {
                img.setProduct(p);
            }
        }*/
        p.setApproved(false);
        return pRepo.save(p);
    }
    private final GmailService gmailService;

    public void approveProduct(Long productId) {
        Product product = pRepo.findById(productId).orElse(null);
        if (product == null) return;

        product.setApproved(true); // ✅ et non setIsApproved
        pRepo.save(product);

        try {
            String userEmail = product.getPostedBy().getEmail();
            String subject = "✅ Your product was approved";
            String content = "Hello,\n\nYour product '" + product.getProductName() + "' has been approved and published on the marketplace.\n\nThank you!";

            gmailService.sendSimpleEmail(userEmail, subject, content);

        } catch (Exception e) {
            System.err.println("❌ Failed to send approval email: " + e.getMessage());
            // Tu peux aussi logger ou relancer une exception custom si nécessaire
        }
    }
    @Override
    public void rejectProduct(Long productId) {
        Product product = pRepo.findById(productId).orElse(null);
        if (product == null) return;

        try {
            String userEmail = product.getPostedBy().getEmail();
            String subject = "❌ Your product was rejected";
            String content = "Hello,\n\nWe regret to inform you that your product '" + product.getProductName() +
                    "' was rejected by the admin and will not be published on the marketplace.\n\n" +
                    "Please make sure your submission follows our guidelines.";

            gmailService.sendSimpleEmail(userEmail, subject, content);
        } catch (Exception e) {
            System.err.println("❌ Failed to send rejection email: " + e.getMessage());
        }

        // Supprimer le produit après l'envoi de l'email
        pRepo.delete(product);
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

