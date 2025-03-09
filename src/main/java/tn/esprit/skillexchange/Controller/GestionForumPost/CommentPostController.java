package tn.esprit.skillexchange.Controller.GestionForumPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;

import tn.esprit.skillexchange.Service.GestionForumPost.ICommentPostService;


import java.util.List;

@RestController
@RequestMapping("/commentPosts")
public class CommentPostController {
    @Autowired
    private ICommentPostService commentPostService;


    @GetMapping("/retrieveCommentPosts")
    public List<CommentPost> getAllComPosts() {
        return commentPostService.retrieveCommentPosts();
    }

    @PostMapping("addComPosts")
    public CommentPost addComPosts(@RequestBody CommentPost commentPost) {
        return commentPostService.add(commentPost);
    }

    @PutMapping("updateComPosts")
    public CommentPost updateComPosts(@RequestBody CommentPost commentPost) {
        return commentPostService.update(commentPost);
    }

    @GetMapping("/retrieveCommentPostById/{commentPost-id}")
    public CommentPost getComPostsById(@PathVariable ("commentPost-id") Long id) {
        return commentPostService.retrieveCommentPostById(id);
    }

    @DeleteMapping("/deleteComPosts/{commentPost-id}")
    public void deleteComPosts(@PathVariable("commentPost-id") Long id) {
        commentPostService.remove(id);
    }
}
