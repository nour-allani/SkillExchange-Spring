package tn.esprit.skillexchange.Service.GestionForumPost;

import tn.esprit.skillexchange.Entity.Emojis;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiComments;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.List;
import java.util.Map;

public interface IEmojiCommentsService {

    List<EmojiComments> retrieveEmojiCommentss();

    EmojiComments add(EmojiComments emojiComment);

    EmojiComments update(EmojiComments emojiComment);

    EmojiComments retrieveEmojiCommentsById(Long id);

    void remove(Long id);

    EmojiComments reactToComment(Long commentId, String email, Emojis emoji);

    void removeReactionFromComment(Long commentId, String email, Emojis emoji);

    boolean hasUserReactedWithEmoji(Long commentId, String email, Emojis emoji);

    List<User> getUsersByEmojiAndCommentId(Long commentId, Emojis emoji);

    Map<String, Long> getEmojiCountsForComment(Long commentId);
}
