package tn.esprit.skillexchange.Controller.GestionForumPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;
import tn.esprit.skillexchange.Service.GestionForumPost.IEmojiPostsService;

import java.util.List;
@RestController
@RequestMapping("/emojiPosts")

public class EmojiPostsController {
    @Autowired
    private IEmojiPostsService emojiPostsService;


    @GetMapping("/retrieveEmojiPostss")
    public List<EmojiPosts> getAllEmojiPostss() {
        return emojiPostsService.retrieveEmojiPostss();
    }

    @PostMapping("/addEmojiPosts")
    public EmojiPosts addEmojiPosts(@RequestBody EmojiPosts emoP) {
        return emojiPostsService.add(emoP);
    }

    @PutMapping("/updateEmojiPosts")
    public EmojiPosts updateEmojiPosts(@RequestBody EmojiPosts emoP) {
        return emojiPostsService.update(emoP);
    }

    @GetMapping("/retrieveEmojiPostsById/{EmojiPosts-id}")
    public EmojiPosts getEmojiPostsById(@PathVariable("EmojiPosts-id") Long id) {
        return emojiPostsService.retrieveEmojiPostsById(id);
    }

    @DeleteMapping("deleteEmojiPosts/{EmojiPostsid}")
    public void deleteEmojiPosts(@PathVariable("EmojiPosts-id") Long id) {
        emojiPostsService.remove(id);
    }
}
