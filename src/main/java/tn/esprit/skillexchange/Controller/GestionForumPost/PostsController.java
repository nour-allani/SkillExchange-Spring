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
import tn.esprit.skillexchange.Repository.GestionForumPost.EmojiPostsRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.GestionForumPost.IEmojiPostsService;
import tn.esprit.skillexchange.Service.GestionForumPost.IPostsService;

import org.springframework.data.domain.Pageable;
import tn.esprit.skillexchange.Service.Mailing.GmailService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")

public class PostsController {

    @Autowired
    private IPostsService postsService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private IEmojiPostsService emojiPostsService;
    @Autowired
    private GmailService gmailService;


    @GetMapping("/retrieveBackPostss")
    public List<Posts> getAllPosts() {
        return postsService.retrievePost();
    }


   @GetMapping("/retrievePostss")
   public Page<Posts> showPosts(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "6") int size) {
       Pageable pageable = PageRequest.of(page, size);
       //return postsService.retrievePostPageable(pageable);
       return postsService.retrieveApprovedPostPageable(pageable);
   }

    @PostMapping("/approvePost/{id}")
    public ResponseEntity<String> approvePost(@PathVariable Long id) {
        Posts post = postsService.retrievePostsById(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }

        post.setApproved(true);  // On approuve le post
        postsService.update(post);  // Met à jour le post dans la base de données

        try {
            // Envoie un email de confirmation à l'utilisateur
            gmailService.sendSimpleEmail(post.getUser().getEmail(), "Your post has been approved",
                    "Congratulations! Your post has been approved and is now visible.");
            return ResponseEntity.ok("Post approved successfully and email sent.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: " + e.getMessage());
        }
    }


    @PostMapping("/rejectPost/{id}")
    public ResponseEntity<String> rejectPost(@PathVariable Long id) {
        Posts post = postsService.retrievePostsById(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }

        postsService.remove(id);  // Supprime le post de la base de données

        try {
            // Envoie un email pour informer l'utilisateur que son post a été rejeté
            gmailService.sendSimpleEmail(post.getUser().getEmail(), "Your post has been rejected",
                    "Sorry, your post has been rejected. Please check the guidelines and try again.");
            return ResponseEntity.ok("Post rejected successfully and email sent.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: " + e.getMessage());
        }
    }





    @PostMapping("/addPosts")
    public Posts addPost(@RequestBody Posts posts) {
        posts.setApproved(false);

        User existingUser = userRepo.findById(posts.getUser().getId()).orElse(null);

        if (existingUser == null) {
            return null;  //
        }

        posts.setUser(existingUser); // Associate a real persisted user
        posts.setApproved(false);
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
    @Autowired
private EmojiPostsRepo emojiPostsRepository;

    @PostMapping("/react")

    public EmojiPosts reactToPost(@RequestBody EmojiReactionDTO reactionDTO) {
        return emojiPostsService.reactToPost(reactionDTO.postId, reactionDTO.email, reactionDTO.emoji);
    }



}
