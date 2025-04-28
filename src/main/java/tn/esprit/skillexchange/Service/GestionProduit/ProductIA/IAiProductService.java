package tn.esprit.skillexchange.Service.GestionProduit.ProductIA;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IAiProductService {

    public Map<String, Object> analyzeImages(MultipartFile[] images);
}
