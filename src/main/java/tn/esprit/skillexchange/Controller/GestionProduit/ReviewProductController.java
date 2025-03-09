package tn.esprit.skillexchange.Controller.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;
import tn.esprit.skillexchange.Service.GestionProduit.IReviewProductService;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/reviewProduct")

public class ReviewProductController {
    private IReviewProductService revpS;

    @GetMapping("/retrieve-ReviewProducts")
    public List<ReviewProduct> getReviewProducts(){
        return revpS.retrieveReviewProducts();

    }
    @GetMapping("/retrieve-all-ReviewProducts/{ReviewProduct-id}")
    public ReviewProduct retrieveReviewProduct(@PathVariable("ReviewProduct-id")Long revpId){
        return revpS.retrieveReviewProductById(revpId);

    }
    @PostMapping("/add-ReviewProduct")
    public ReviewProduct addReviewProduct(@RequestBody ReviewProduct b) {
        return revpS.addReviewProduct(b);

    }

    @DeleteMapping("/remove-ReviewProduct/{ReviewProduct-id}")
    public void removeReviewProduct(@PathVariable("ReviewProduct-id") Long revpId) {
        revpS.removeReviewProduct(revpId);
    }

    @PutMapping("/modify-ReviewProduct")
    public ReviewProduct modifyReviewProduct(@RequestBody ReviewProduct revp) {
        return revpS.modifyReviewProduct(revp);

    }

}
