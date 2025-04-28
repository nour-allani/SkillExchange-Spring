package tn.esprit.skillexchange.Service.GestionForumPost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionForumPost.ImagePost;
import tn.esprit.skillexchange.Repository.GestionForumPost.PostImageRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PostImageServiceImpl   implements IPostImageService{
@Autowired
PostImageRepo impRepo;

    @Override
    public List<ImagePost> retrieveImagePosts() {
        return impRepo.findAll();
    }

    @Override
    public ImagePost retrieveImagePostById(Long ImageId) {
        return impRepo.findById(ImageId).get();
    }

    @Override
    public ImagePost addImagePost(ImagePost im) {
        return impRepo.save(im);
    }

    @Override
    public void removeImagePost(Long ImageId) {
        impRepo.deleteById(ImageId);

    }

    @Override
    public ImagePost modifyImagePost(ImagePost imageProd) {
        return impRepo.save(imageProd);
    }
}
