package tn.esprit.skillexchange.Controller.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionProduit.Product;
import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;
import tn.esprit.skillexchange.Service.GestionProduit.IProductService;

import java.util.List;


@RestController
@AllArgsConstructor

@RequestMapping("/product")

public class ProductController {
    @Autowired
    private IProductService pS;

    @GetMapping("/retrieve-products")
    public List<Product> getProducts(){
        return pS.retrieveProducts();

    }
    @GetMapping("/retrieve-products/{product-id}")
    public Product retrieveProduct(@PathVariable("product-id")Long pId){
        return pS.retrieveProductById(pId);

    }
    @PostMapping("/add-product")
    public Product addProduct(@RequestBody Product p) {
        return pS.addProduct(p);

    }

    @DeleteMapping("/remove-product/{product-id}")
    public void removeProduct(@PathVariable("product-id") Long pId) {
        pS.removeProduct(pId);
    }

    @PatchMapping("/modify-product")
    public Product modifyImageProduct(@RequestBody Product p) {
        return pS.modifyProduct(p);

    }
    @PostMapping("/{productId}/reviews")
    public ReviewProduct addProductReview(@PathVariable Long productId, @RequestBody ReviewProduct review) {
        return pS.addReviewToProduct(productId, review);
    }

}
