package tn.esprit.skillexchange.Service.GestionForumPost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comments;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.Emojis;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiComments;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionForumPost.CommentPostRepo;
import tn.esprit.skillexchange.Repository.GestionForumPost.EmojisCommentRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class EmojiCommentsServiceImpl implements IEmojiCommentsService {

    private final EmojisCommentRepo emojiCommentRepo;
    private final CommentPostRepo CommentR;
    private final UserRepo UserR;


    @Override
    public List<EmojiComments> retrieveEmojiCommentss() {
        return emojiCommentRepo.findAll();
    }

    @Override
    public EmojiComments add(EmojiComments emojiComment) {
        return emojiCommentRepo.save(emojiComment);
    }

    @Override
    public EmojiComments update(EmojiComments emojiComment) {
        return emojiCommentRepo.save(emojiComment);
    }

    @Override
    public EmojiComments retrieveEmojiCommentsById(Long id) {
        return emojiCommentRepo.findById(id).orElse(null);
    }

    @Override
    public void remove(Long id) {
        emojiCommentRepo.deleteById(id);
    }

    @Override
    public EmojiComments reactToComment(Long commentId, String email, Emojis emoji) {
      /*  CommentPost commentPost = commentPostRepo.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Commentaire introuvable"));
        EmojiComments existing = emojiCommentRepo.findByUserAndComment(email, commentId);
        if (existing != null) {
            emojiCommentRepo.delete(existing);
        }
        EmojiComments emojiComment = new EmojiComments();
        emojiComment.setCommentPost(commentPost);
        emojiComment.setEmail(email);
        emojiComment.setEmoji(Emojis.valueOf(emoji));
        return emojiCommentRepo.save(emojiComment);*/
        EmojiComments existingReaction = emojiCommentRepo.findByUserAndComment(email, commentId);
        if (existingReaction != null) {
            existingReaction.setEmoji(emoji);
            existingReaction.setReactedAt(LocalDateTime.now());
            return emojiCommentRepo.save(existingReaction);
        } else {
            EmojiComments reaction = new EmojiComments();
            reaction.setEmoji(emoji);
            reaction.setReactedAt(LocalDateTime.now());

            CommentPost comment = CommentR.findById(commentId).orElseThrow(() -> new RuntimeException("comment non trouvé avec l'ID : " + commentId));
            reaction.setCommentPost(comment);


            User user = UserR.findByEmail(email).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + email));

            reaction.setUser(user);

            return emojiCommentRepo.save(reaction);
        }}


       @Override
       public void removeReactionFromComment(Long commentId, String email, Emojis emoji) {
           EmojiComments existingReaction = emojiCommentRepo.findByUserAndComment(email, commentId);

           if (existingReaction != null && existingReaction.getEmoji().equals(emoji)) {
               emojiCommentRepo.delete(existingReaction);  // Supprimer la réaction existante
           } else {
               throw new RuntimeException("Réaction non trouvée à supprimer.");
           }}


           @Override
           public boolean hasUserReactedWithEmoji(Long commentId, String email, Emojis emoji) {
               EmojiComments existingReaction = emojiCommentRepo.findByUserAndComment(email, commentId);
               return existingReaction != null && existingReaction.getEmoji().equals(emoji);
           }

        /*@Override
        public List<String> getUsersByEmojiAndCommentId (Long commentId, Emojis emoji){
            return emojiCommentRepo.findEmailsByEmojiAndCommentId(commentId, emoji);
        }


        @Override
        public List<Object[]> getEmojiCountsForComment (Long commentId){
               return emojiCommentRepo.countEmojisByCommentId(commentId);} */
    @Override
    public List<User> getUsersByEmojiAndCommentId(Long commentId, Emojis emoji) {
        return emojiCommentRepo.findByUserAndComment(commentId, emoji);
    }
        @Override
        public Map<String, Long> getEmojiCountsForComment(Long commentId) {
            List<Object[]> results = emojiCommentRepo.countEmojisByCommentId(commentId);
            Map<String, Long> counts = new HashMap<>();
            for (Object[] result : results) {
                String emoji = result[0].toString();
                Long count = (Long) result[1];
                counts.put(emoji, count);
            }
            return counts;
        }





}
