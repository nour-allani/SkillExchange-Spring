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
    public List<Product> getAllApprovedProducts() {

        return pRepo.findByIsApprovedTrue();
    }
    @Override
    public Product retrieveProductById(Long ProductId) {
        return pRepo.findById(ProductId).get();
    }

    @Override
    public Product addProduct(Product p) {
        if (p.getImageProducts() != null) {
            for (ImageProduct img : p.getImageProducts()) {
                img.setProduct(p);
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

        if (p.getImageProducts() != null) {
            for (ImageProduct img : p.getImageProducts()) {
                img.setProduct(p);
            }
        }

       // p.setApproved(false);
        return pRepo.save(p);
    }

    private final GmailService gmailService;

    public void approveProduct(Long productId) {
        Product product = pRepo.findById(productId).orElse(null);
        if (product == null) return;

        product.setApproved(true);
        pRepo.save(product);
        try {
            String userEmail = product.getPostedBy().getEmail();
            gmailService.sendProductApprovalHtmlEmail(userEmail, product.getProductName());
        } catch (Exception e) {
            System.err.println("❌ Failed to send approval email: " + e.getMessage());
        }

    }
    @Override
    public void rejectProduct(Long productId) {
        Product product = pRepo.findById(productId).orElse(null);
        if (product == null) return;
        try {
            String userEmail = product.getPostedBy().getEmail();
            gmailService.sendProductRejectionHtmlEmail(userEmail, product.getProductName());
        } catch (Exception e) {
            System.err.println("❌ Failed to send rejection email: " + e.getMessage());
        }

        pRepo.delete(product);
    }



    public ReviewProduct addReviewToProduct(Long productId, ReviewProduct review) {
        Product product = pRepo.findById(productId).orElse(null);

        if (product != null) {

            review.setProduct(product);
            return reviewRepo.save(review);
        } else {
            return null;
        }
    }


}

