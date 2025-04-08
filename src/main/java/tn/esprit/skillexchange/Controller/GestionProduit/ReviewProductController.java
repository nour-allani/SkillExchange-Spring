package tn.esprit.skillexchange.Controller.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import tn.esprit.skillexchange.Entity.GestionProduit.ReviewProduct;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.GestionProduit.IReviewProductService;

import java.util.Date;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/reviewProduct")

public class ReviewProductController {
    @Autowired
    private IReviewProductService revpS;
    @Autowired
    private UserRepo u;




    @GetMapping("/retrieve-reviews-by-product/{productId}")
    public List<ReviewProduct> getReviewsByProductId(@PathVariable("productId") Long productId) {
        return revpS.retrieveReviewsProductById(productId);
    }
    @PostMapping("/add-ReviewProduct")
    public ReviewProduct addReviewProduct(@RequestBody ReviewProduct b) {


        return revpS.addReviewProduct(b);

    }

    @DeleteMapping("/remove-ReviewProduct/{ReviewProduct-id}")
    public void removeReviewProduct(@PathVariable("ReviewProduct-id") Long revpId) {
        revpS.removeReviewProduct(revpId);
    }

    @PatchMapping("/modify-ReviewProduct")
    public ReviewProduct modifyReviewProduct(@RequestBody ReviewProduct revp) {
        return revpS.modifyReviewProduct(revp);

    }

}


