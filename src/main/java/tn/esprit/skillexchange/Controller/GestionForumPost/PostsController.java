package tn.esprit.skillexchange.Controller.GestionForumPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import tn.esprit.skillexchange.Service.GestionForumPost.IPostsService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private IPostsService postsService;


    @GetMapping("/retrievePostss")
    public List<Posts> getAllPostss() {
        return postsService.retrievePost();
    }

    @PostMapping ("/addPosts")
    public Posts addPosts(@RequestBody Posts posts) {
        return postsService.add(posts);
    }

    @PutMapping("/updatePosts")
    public Posts updatePosts(@RequestBody Posts posts) {
        return postsService.update(posts);
    }

    @GetMapping("/retrievePostsById/{post-id}")
    public Posts getPostsById(@PathVariable("post-id") Long id) {
        return postsService.retrievePostsById(id);
    }

    @DeleteMapping("/deletePosts/{post-id}")
    public void deletePosts(@PathVariable("post-id") Long id) {
        postsService.remove(id);
    }
}
