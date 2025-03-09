package tn.esprit.skillexchange.Service.GestionProduit;

import tn.esprit.skillexchange.Entity.GestionProduit.ImageProduct;

import java.util.List;

public interface IImageProductService {
    public List<ImageProduct> retrieveImageProducts();
    public ImageProduct retrieveImageProductById(Long ImageProductId);
    public ImageProduct addImageProduct(ImageProduct im);
    public void removeImageProduct(Long ImageProductId);
    public ImageProduct modifyImageProduct(ImageProduct imageProd);
}
