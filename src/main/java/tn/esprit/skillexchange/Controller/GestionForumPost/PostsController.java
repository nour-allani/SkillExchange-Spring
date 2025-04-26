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
    public ResponseEntity<Posts> approvePost(@PathVariable Long id) {
        Posts post = postsService.retrievePostsById(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        post.setApproved(true);
        postsService.update(post);

        try {
            gmailService.sendSimpleEmail(
                    post.getUser().getEmail(),
                    "Your post has been approved",
                    "Congratulations! Your post has been approved and is now visible."
            );
        } catch (Exception e) {
            // Continue même si l'email échoue
        }

        return ResponseEntity.ok(post); // ✅ Retourne le post mis à jour
    }

    @PostMapping("/rejectPost/{id}")
    public ResponseEntity<Posts> rejectPost(@PathVariable Long id) {
        Posts post = postsService.retrievePostsById(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        postsService.remove(id);

        try {
            gmailService.sendSimpleEmail(
                    post.getUser().getEmail(),
                    "Your post has been rejected",
                    "Sorry, your post has been rejected. Please check the guidelines and try again."
            );
        } catch (Exception e) {
            // Continue même si l'email échoue
        }

        return ResponseEntity.ok(post); // ✅ Retourne l'objet supprimé pour référence
    }






    @PostMapping("/addPosts")
    public Posts addPost(@RequestBody Posts posts) {
        posts.setApproved(false);

        User existingUser = userRepo.findById(posts.getUser().getId()).orElse(null);

        if (existingUser == null) {
            return null;  //
        }

        posts.setUser(existingUser);
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
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Autowired
private EmojiPostsRepo emojiPostsRepository;

    @PostMapping("/react")

    public EmojiPosts reactToPost(@RequestBody EmojiReactionDTO reactionDTO) {
        return emojiPostsService.reactToPost(reactionDTO.postId, reactionDTO.email, reactionDTO.emoji);
    }



}
