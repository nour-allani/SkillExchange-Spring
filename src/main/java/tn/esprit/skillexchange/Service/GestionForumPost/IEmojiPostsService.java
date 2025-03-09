package tn.esprit.skillexchange.Service.GestionForumPost;
import java.util.List;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;

public interface IEmojiPostsService {
    List<EmojiPosts> retrieveEmojiPostss();
    EmojiPosts add(EmojiPosts emP);
    EmojiPosts update(EmojiPosts emP);
    EmojiPosts retrieveEmojiPostsById(Long id);
    void remove(Long id);
}
