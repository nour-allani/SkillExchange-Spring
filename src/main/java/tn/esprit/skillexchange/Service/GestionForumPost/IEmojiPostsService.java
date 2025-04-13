package tn.esprit.skillexchange.Service.GestionForumPost;

import tn.esprit.skillexchange.Entity.Emojis;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;

import java.util.List;
import java.util.Map;

public interface IEmojiPostsService {
    List<EmojiPosts> retrieveEmojiPostss();
    EmojiPosts add(EmojiPosts emP);
    EmojiPosts update(EmojiPosts emP);
    EmojiPosts retrieveEmojiPostsById(Long id);
    void remove(Long id);
    Map<String, Long> countEmojisByPostId(Long postId);
    EmojiPosts reactToPost(Long postId/*, Long userId*/ ,String  email, Emojis newEmoji);
}
