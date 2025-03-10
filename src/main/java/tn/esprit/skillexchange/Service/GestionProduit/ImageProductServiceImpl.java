package tn.esprit.skillexchange.Service.GestionProduit;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.ImageProduct;
import tn.esprit.skillexchange.Repository.GestionProduit.ImageProductRepo;

import java.util.List;
@Service
@AllArgsConstructor
public class ImageProductServiceImpl implements  IImageProductService{
    @Autowired
    ImageProductRepo impRepo;
    @Override
    public List<ImageProduct> retrieveImageProducts() {
        return impRepo.findAll();
    }

    @Override
    public ImageProduct retrieveImageProductById(Long ImageProductId) {
        return impRepo.findById(ImageProductId).get();
    }

    @Override
    public ImageProduct addImageProduct(ImageProduct im) {
        return impRepo.save(im);
    }

    @Override
    public void removeImageProduct(Long ImageProductId) {
            impRepo.deleteById(ImageProductId);
    }

    @Override
    public ImageProduct modifyImageProduct(ImageProduct imageProd) {
        return impRepo.save(imageProd);
    }
}
