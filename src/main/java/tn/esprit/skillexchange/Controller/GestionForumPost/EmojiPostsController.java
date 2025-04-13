package tn.esprit.skillexchange.Controller.GestionForumPost;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.Emojis;
import tn.esprit.skillexchange.Entity.GestionForumPost.DTO.EmojiReactionDTO;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;
import tn.esprit.skillexchange.Service.GestionForumPost.IEmojiPostsService;

import java.util.Map;


//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/emojiPosts")
@AllArgsConstructor
public class EmojiPostsController {

    private final IEmojiPostsService emojiPostsService;

   /* @PostMapping("/react")
    public EmojiPosts reactToPost(@RequestBody EmojiReactionDTO reactionDTO) {
        return emojiPostsService.reactToPost(reactionDTO.postId, reactionDTO.userId, reactionDTO.emoji);
    }*/


    @GetMapping("/counts/{postId}")
    public Map<String, Long> getEmojiCounts(@PathVariable Long postId) {
        return emojiPostsService.countEmojisByPostId(postId);
    }
}
