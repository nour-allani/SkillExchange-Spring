package tn.esprit.skillexchange.Controller.GestionForumPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.Emojis;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiComments;
import tn.esprit.skillexchange.Entity.GestionForumPost.DTO.EmojiReactionForCommentDTO;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Service.GestionForumPost.IEmojiCommentsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emojiComments")
public class EmojiCommentsController {

    @Autowired
    private IEmojiCommentsService emojiCommentsService;

    @GetMapping("/counts/{commentId}")
    public Map<String, Long> getEmojiCounts(@PathVariable Long commentId) {
        return emojiCommentsService.getEmojiCountsForComment(commentId);
    }
    @GetMapping("/users/{commentId}/{emoji}")
    public List<User> getUsersByEmojiAndCommentId(@PathVariable Long commentId, @PathVariable Emojis emoji) {
        return emojiCommentsService.getUsersByEmojiAndCommentId(commentId, emoji);
    }
    @GetMapping("/{commentId}/users/{userEmail}/{emoji}")
    public ResponseEntity<Boolean> hasUserReactedWithEmoji(@PathVariable Long commentId, @PathVariable String userEmail, @PathVariable String emoji) {
        boolean hasReacted = emojiCommentsService.hasUserReactedWithEmoji(commentId, userEmail, Emojis.valueOf(emoji));
        return ResponseEntity.ok(hasReacted);
    }
    @DeleteMapping("/{commentId}/users/{userEmail}/{emoji}")
    public ResponseEntity<Void> removeReaction(@PathVariable Long commentId, @PathVariable String userEmail, @PathVariable String emoji) {
        emojiCommentsService.removeReactionFromComment(commentId, userEmail, Emojis.valueOf(emoji));
        return ResponseEntity.noContent().build();
    }









}
