package tn.esprit.skillexchange.Controller.GestionForumPost;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionForumPost.ImagePost;
import tn.esprit.skillexchange.Service.GestionForumPost.IPostImageService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/imagePost")

public class ImagePostController {
    @Autowired
    private IPostImageService impS;

    @GetMapping("/retrieve-ImagePosts")
    public List<ImagePost> getImagePosts(){
        return impS.retrieveImagePosts();

    }
    @GetMapping("/retrieve-all-ImagePosts/{ImagePost-id}")
    public ImagePost retrieveImagePost(@PathVariable("ImagePost-id")Long impId){
        return impS.retrieveImagePostById(impId);

    }
    @PostMapping("/add-ImagePost")
    public ImagePost addImagePost(@RequestBody ImagePost b) {
        return impS.addImagePost(b);

    }

    @DeleteMapping("/{id}")
    public void removeImagePost(@PathVariable("id") Long id) {
        impS.removeImagePost(id);
    }

    @PatchMapping("/modify-ImagePost")
    public ImagePost modifyImagePost(@RequestBody ImagePost imp) {
        return impS.modifyImagePost(imp);

    }
}
