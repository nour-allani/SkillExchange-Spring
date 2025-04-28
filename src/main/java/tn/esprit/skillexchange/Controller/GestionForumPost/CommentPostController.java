
package tn.esprit.skillexchange.Controller.GestionForumPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;

import tn.esprit.skillexchange.Entity.GestionForumPost.DTO.EmojiReactionDTO;
import tn.esprit.skillexchange.Entity.GestionForumPost.DTO.EmojiReactionForCommentDTO;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiComments;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import tn.esprit.skillexchange.Repository.GestionForumPost.PostsRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.GestionForumPost.*;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private OpenAiService openAiService;
    @Autowired
    private HuggingFaceService huggingFaceService;



    @GetMapping("/retrieveCommentPosts")
    public List<CommentPost> getAllComPosts() {
        return commentPostService.retrieveCommentPosts();
    }

//original
  /* @PostMapping("addComPosts")
    public  CommentPost addComPosts(@RequestBody CommentPost com,
                                    @RequestParam Long postId) {
        System.out.println("Comment reçu: " + com);
        Posts post = pRepo.findById(postId).orElse(null);

        if (post == null) {
            // Retourne null ou une Review vide ou un message d'erreur custom si besoin
            return null;
        }


        com.setPost(post); // associer le produit
        com.setCreatedAt(new Date());
        com.setUpdatedAt(new Date());

        return commentPostService.add(com);


    }*/
    ///v2
  /* @PostMapping("addComPosts")
   public ResponseEntity<CommentPost> addComPosts(@RequestBody CommentPost com, @RequestParam Long postId) {
       System.out.println("Comment reçu: " + com);
       Posts post = pRepo.findById(postId).orElse(null);

       if (post == null) {
           return ResponseEntity.badRequest().build();
       }

       com.setPost(post);
       com.setCreatedAt(new Date());
       com.setUpdatedAt(new Date());

       // OpenAI : Générer et bloquer jusqu'à réponse
       String generatedComment = openAiService.generateComment(post.getContent()).block();

       com.setContent(generatedComment != null ? generatedComment : "Default comment");
       CommentPost savedComment = commentPostService.add(com);

       return ResponseEntity.ok(savedComment);
   }
*/
/*@PostMapping("addComPosts")
public ResponseEntity<CommentPost> addComPosts(@RequestBody CommentPost com, @RequestParam Long postId) {
    System.out.println("Comment reçu: " + com);

    // Récupérer le post par son ID
    Posts post = pRepo.findById(postId).orElse(null);

    if (post == null) {
        return ResponseEntity.badRequest().body(null);
    }

    // Associer le commentaire au post
    com.setPost(post);
    com.setCreatedAt(new Date());
    com.setUpdatedAt(new Date());

    // Appel à l'API OpenAI pour générer un commentaire si le contenu est vide
    if (com.getContent() == null || com.getContent().isEmpty()) {
        String generatedComment = openAiService.generateComment(post.getContent()).block();
        com.setContent(generatedComment != null ? generatedComment : "Default comment");
    }

    // Sauvegarder le commentaire
    CommentPost savedComment = commentPostService.add(com);
    return ResponseEntity.ok(savedComment);
}*/


   /* @PostMapping("/generateComment")
    public ResponseEntity<String> generateComment(@RequestBody String postContent) {
        // Appel à l'API OpenAI pour générer un commentaire
        String generatedComment = openAiService.generateComment(postContent).block();  // Par exemple, appel synchrone ici

        if (generatedComment == null || generatedComment.isEmpty()) {
            return new ResponseEntity<>("No comment generated", HttpStatus.BAD_REQUEST);
        }

        // Traitement du commentaire généré
        return new ResponseEntity<>(generatedComment, HttpStatus.OK);
    }*/
  /* @PostMapping("/generateComment")
   public ResponseEntity<String> generateComment(@RequestBody Map<String, Long> body) {
       System.out.println("Request Body: " + body);
       Long postId = body.get("postId");
       if (postId == null) {
           return new ResponseEntity<>("postId is required", HttpStatus.BAD_REQUEST);
       }

       // Récupérer le post à partir de l'ID
       Posts post = pRepo.findById(postId).orElse(null);
       if (post == null) {
           return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
       }

       // Récupérer le contenu du post
       String postContent = post.getContent();
       if (postContent == null || postContent.isEmpty()) {
           return new ResponseEntity<>("Post content is empty", HttpStatus.BAD_REQUEST);
       }

       // Générer le commentaire à l'aide de OpenAI
       String generatedComment = openAiService.generateComment(postContent).block();

       if (generatedComment == null || generatedComment.isEmpty()) {
           return new ResponseEntity<>("No comment generated", HttpStatus.BAD_REQUEST);
       }

       return new ResponseEntity<>(generatedComment, HttpStatus.OK);
   }*/
   // CommentPostController.java
   // Ton endpoint normal pour ajouter un commentaire (avec génération si vide)
   @PostMapping("addComPosts")
   public ResponseEntity<CommentPost> addComPosts(@RequestBody CommentPost com, @RequestParam Long postId) {
       System.out.println("Comment reçu: " + com);

       Posts post = pRepo.findById(postId).orElse(null);
       if (post == null) {
           return ResponseEntity.badRequest().build();
       }

       com.setPost(post);
       com.setCreatedAt(new Date());
       com.setUpdatedAt(new Date());

       if (com.getContent() == null || com.getContent().isEmpty()) {
           // Version corrigée : attendre la réponse HuggingFace
           String generatedComment = huggingFaceService.generateComment(post.getContent()).block();
           com.setContent(generatedComment != null ? generatedComment : "Default comment");
       }

       CommentPost savedComment = commentPostService.add(com);
       return ResponseEntity.ok(savedComment);
   }


    // NOUVEAU: Endpoint pour seulement GÉNÉRER (pas sauvegarder)
    @PostMapping("/generateComment")
    public Mono<ResponseEntity<Map<String, String>>> generateComment(@RequestParam Long postId) {
        // Récupérer le contenu du post en utilisant l'ID
        String postContent = getPostContentById(postId);  // Remplace par ta logique pour récupérer le contenu du post par ID
        if (postContent == null || postContent.trim().isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Post content must not be empty.");
            return Mono.just(ResponseEntity.badRequest().body(response));
        }

        return huggingFaceService.generateComment(postContent)
                .map(generatedComment -> {
                    Map<String, String> response = new HashMap<>();
                    if (generatedComment == null || generatedComment.trim().isEmpty()) {
                        response.put("message", "No comment generated.");
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
                    } else {
                        response.put("generatedComment", generatedComment);
                        return ResponseEntity.ok(response);
                    }
                })
                .onErrorResume(error -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("error", "Error generating comment.");
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
                });
    }


    private String getPostContentById(Long postId) {
        // Logic to retrieve the post content by ID (replace with your actual logic)
        // For example:
        Posts post = pRepo.findById(postId).orElse(null);
        return post != null ? post.getContent() : null;
    }



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
    @PatchMapping("/{id}")
    public CommentPost update(@PathVariable("id") Long id, @RequestBody CommentPost commentP) {
        commentP.setIdComment(id);

        CommentPost updatedReview = commentPostService.update(commentP);


        return updatedReview;
    }
}
