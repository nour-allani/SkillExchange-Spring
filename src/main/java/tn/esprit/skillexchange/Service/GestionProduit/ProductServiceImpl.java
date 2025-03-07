package tn.esprit.skillexchange.Service.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;
import tn.esprit.skillexchange.Repository.GestionProduit.ProductRepo;

import java.util.List;
@Service
@AllArgsConstructor
public class ProductServiceImpl implements  IProductService{
   ProductRepo pRepo;
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
}
