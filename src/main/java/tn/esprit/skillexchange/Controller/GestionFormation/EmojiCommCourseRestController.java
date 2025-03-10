package tn.esprit.skillexchange.Controller.GestionFormation;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionFormation.EmojisCommentCourse;
import tn.esprit.skillexchange.Service.GestionFormation.EmojisCommCourseService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/emojisCommentCourse")
public class EmojiCommCourseRestController {
    @Autowired
    EmojisCommCourseService emojiService;


    @GetMapping("/retrieve-all-emojis")
    public List<EmojisCommentCourse> getEmojis() {
        List<EmojisCommentCourse> listEmojis = emojiService.retrieveAllEmojis();
        return listEmojis;
    }

    @GetMapping("/retrieve-emoji/{emoji-id}")
    public EmojisCommentCourse retrieveCourse(@PathVariable("emoji-id") Long emojiId) {
        EmojisCommentCourse emoji = emojiService.retrieveEmojisCommentCourse(emojiId);
        return emoji;
    }

    @PostMapping("/add-emoji")
    public EmojisCommentCourse addEmoji(@RequestBody EmojisCommentCourse c) {
        EmojisCommentCourse emoji = emojiService.addEmojiComment(c);
        return emoji;
    }

    @DeleteMapping("/remove-emoji/{emoji-id}")
    public void removeEmoji(@PathVariable("emoji-id") Long emojiId) {
        emojiService.removeEmojisCommentCourse(emojiId);
    }



    @PutMapping("/modify-emoji")
    public EmojisCommentCourse modifyCourse(@RequestBody EmojisCommentCourse c) {
        EmojisCommentCourse emoji = emojiService.modifyEmojiComment(c);
        return emoji;
    }
}
