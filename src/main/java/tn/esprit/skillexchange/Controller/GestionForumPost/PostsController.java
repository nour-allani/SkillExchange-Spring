package tn.esprit.skillexchange.Controller.GestionForumPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionForumPost.DTO.EmojiReactionDTO;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;
import tn.esprit.skillexchange.Entity.GestionForumPost.ImagePost;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.GestionForumPost.IEmojiPostsService;
import tn.esprit.skillexchange.Service.GestionForumPost.IPostsService;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")

public class PostsController {

    @Autowired
    private IPostsService postsService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private IEmojiPostsService emojiPostsService;

   // @GetMapping("/retrieveBackPostss")
    //public List<Posts> getAllPosts() {
       // return postsService.retrievePost();
    //}
   @GetMapping("/retrievePostss")
   public Page<Posts> showPosts(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "6") int size) {
       Pageable pageable = PageRequest.of(page, size);
       return postsService.retrievePostPageable(pageable);
   }



    @PostMapping("/addPosts")
    public Posts addProduct(@RequestBody Posts posts) {

        User existingUser = userRepo.findById(posts.getUser().getId()).orElse(null);

        if (existingUser == null) {
            return null;  // Or return a default value, like an empty product if needed
        }

        posts.setUser(existingUser); // Associate a real persisted user
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

    //@DeleteMapping("/deletePosts/{post-id}")
    //public void deletePosts(@PathVariable("post-id") Long id) {
      //  postsService.remove(id);
    //}
    @DeleteMapping("/deletePosts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Posts post = postsService.retrievePostsById(id);

        if (post != null) {
            postsService.remove(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    @PostMapping("/react")
    //@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    public EmojiPosts reactToPost(@RequestBody EmojiReactionDTO reactionDTO) {
        return emojiPostsService.reactToPost(reactionDTO.postId, reactionDTO.email, reactionDTO.emoji);
    }


}
