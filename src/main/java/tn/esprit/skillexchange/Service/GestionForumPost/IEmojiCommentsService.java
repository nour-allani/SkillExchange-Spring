package tn.esprit.skillexchange.Service.GestionForumPost;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiComments;

import java.util.List;

public interface IEmojiCommentsService {
    List<EmojiComments> retrieveEmojiCommentss();
    EmojiComments add(EmojiComments banned);
    EmojiComments update(EmojiComments banned);
    EmojiComments retrieveEmojiCommentsById(Long id);
    void remove(Long id);

}
