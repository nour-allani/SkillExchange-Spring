package tn.esprit.skillexchange.Service.GestionForumPost;

import tn.esprit.skillexchange.Entity.GestionForumPost.ImagePost;

import java.util.List;

public interface IPostImageService {
    public List<ImagePost> retrieveImagePosts();
    public ImagePost retrieveImagePostById(Long ImageId);
    public ImagePost addImagePost(ImagePost im);
    public void removeImagePost(Long ImageId);
    public ImagePost modifyImagePost(ImagePost imageProd);
}
