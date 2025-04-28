
package tn.esprit.skillexchange.Controller.GestionForumPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;

import tn.esprit.skillexchange.Entity.GestionForumPost.DTO.EmojiReactionDTO;
import tn.esprit.skillexchange.Entity.GestionForumPost.DTO.EmojiReactionForCommentDTO;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiComments;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import tn.esprit.skillexchange.Repository.GestionForumPost.PostsRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.GestionForumPost.ICommentPostService;
import tn.esprit.skillexchange.Service.GestionForumPost.IEmojiCommentsService;
import tn.esprit.skillexchange.Service.GestionForumPost.IEmojiPostsService;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/commentPosts")
public class CommentPostController {

    @Autowired
    private ICommentPostService commentPostService;
    @Autowired
    private IEmojiCommentsService emojiCommentService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostsRepo pRepo;
    @GetMapping("/retrieveCommentPosts")
    public List<CommentPost> getAllComPosts() {
        return commentPostService.retrieveCommentPosts();
    }

    /*@PostMapping("addComPosts")
    public CommentPost addComPosts(@RequestBody CommentPost commentPost) {
        return commentPostService.add(commentPost);
    }
*/
    //@PostMapping("addComPosts/{postId}")
    //public ResponseEntity<CommentPost> addComPosts(@PathVariable Long postId, @RequestBody CommentPost commentPost) {
    // Ajouter l'ID du post au commentaire
    //  commentPost.getPost().setIdPost(postId);

    // Appeler le service pour ajouter le commentaire
    //CommentPost addedComment = commentPostService.add(commentPost);

    //if (addedComment == null) {
    // Retourner une réponse indiquant que le commentaire n'a pas pu être ajouté
    //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    //   }

    // Retourner la réponse avec le commentaire ajouté
    //return ResponseEntity.status(HttpStatus.CREATED).body(addedComment);
    // }
   // @PostMapping("addComPosts")
    //public  CommentPost addComPosts(@RequestBody CommentPost com,
     //                               @RequestParam Long postId) {
     //   System.out.println("Comment reçu: " + com);
     //   Posts post = pRepo.findById(postId).orElse(null);

      //  if (post == null) {
      //      // Retourne null ou une Review vide ou un message d'erreur custom si besoin
       //     return null;
      //  }

       // com.setPost(post); // associer le produit
       // com.setCreatedAt(new Date());
        //com.setUpdatedAt(new Date());

       // return commentPostService.add(com);
   // }


    @PutMapping("updateComPosts")
    public CommentPost updateComPosts(@RequestBody CommentPost commentPost) {
        return commentPostService.update(commentPost);
    }

    /*  @GetMapping("/retrieveCommentPostById/{commentPost-id}")
      public CommentPost getComPostsById(@PathVariable ("commentPost-id") Long id) {
          return commentPostService.retrieveCommentPostById(id);
      }*/
    @GetMapping("/retrieveCommentPostsByPost/{postId}")
    public List<CommentPost> getCommentsByPostId(@PathVariable Long postId) {
        return commentPostService.retrieveCommentPostsByPostId(postId);
    }

    @DeleteMapping("/deleteComPosts/{commentPost-id}")
    public void deleteComPosts(@PathVariable("commentPost-id") Long id) {
        commentPostService.remove(id);
    }

    @PostMapping("/reactCom")

    public EmojiComments reactToComment(@RequestBody EmojiReactionForCommentDTO reactionDTO) {
        return emojiCommentService.reactToComment(reactionDTO.commentId, reactionDTO.email, reactionDTO.emoji);
    }
}
