package tn.esprit.skillexchange.Controller.GestionProduit;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionProduit.ImageProduct;
import tn.esprit.skillexchange.Service.GestionProduit.IImageProductService;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/imageProd")

public class ImageProductController {
   @Autowired
    private IImageProductService impS;

    @GetMapping("/retrieve-ImageProducts")
    public List<ImageProduct> getImageProducts(){
        return impS.retrieveImageProducts();

    }
    @GetMapping("/retrieve-all-ImageProducts/{ImageProduct-id}")
    public ImageProduct retrieveImageProduct(@PathVariable("ImageProduct-id")Long impId){
        return impS.retrieveImageProductById(impId);

    }
    @PostMapping("/add-ImageProduct")
    public ImageProduct addImageProduct(@RequestBody ImageProduct b) {
        return impS.addImageProduct(b);

    }

    @DeleteMapping("/{id}")
    public void removeImageProduct(@PathVariable("id") Long id) {
        impS.removeImageProduct(id);
    }

    @PatchMapping("/modify-ImageProduct")
    public ImageProduct modifyImageProduct(@RequestBody ImageProduct imp) {
        return impS.modifyImageProduct(imp);

    }

}
