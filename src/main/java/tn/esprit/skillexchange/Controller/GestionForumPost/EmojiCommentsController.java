package tn.esprit.skillexchange.Controller.GestionForumPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiComments;
import tn.esprit.skillexchange.Service.GestionForumPost.IEmojiCommentsService;

import java.util.List;

@RestController
@RequestMapping("/emojiComments")
public class EmojiCommentsController {
    @Autowired
    private IEmojiCommentsService emojiCommentsService;


    @GetMapping("/retrieveEmojiCommentss")
    public List<EmojiComments> getAllEmoCom() {
        return emojiCommentsService.retrieveEmojiCommentss();
    }

    @PostMapping("/addEmoCom")
    public EmojiComments addEmoCom(@RequestBody EmojiComments emoC) {
        return emojiCommentsService.add(emoC);
    }

    @PutMapping("/updateEmoCom")
    public EmojiComments updateEmoCom(@RequestBody EmojiComments emoC) {
        return emojiCommentsService.update(emoC);
    }

    @GetMapping("/retrieveEmojiCommentsById/{emojiComment-id}")
    public EmojiComments getEmoComById(@PathVariable ("emojiComment-id") Long id) {
        return emojiCommentsService.retrieveEmojiCommentsById(id);
    }

    @DeleteMapping("/deleteEmoCom/{emojiComment-id}")
    public void deleteEmoCom(@PathVariable ("emojiComment-id") Long id) {
        emojiCommentsService.remove(id);
    }
}
