package tn.esprit.skillexchange.Service.GestionForumPost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.Emojis;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionForumPost.EmojiPostsRepo;
import tn.esprit.skillexchange.Repository.GestionForumPost.PostsRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class EmojiPostsServiceImpl implements IEmojiPostsService {

    private final EmojiPostsRepo emoPost;
    private final PostsRepo postR;
    private final UserRepo UserR;


    @Override
    public List<EmojiPosts> retrieveEmojiPostss() {
        return emoPost.findAll();
    }

    @Override
    public EmojiPosts add(EmojiPosts emP) {
        return emoPost.save(emP);
    }

    @Override
    public EmojiPosts update(EmojiPosts emP) {
        return emoPost.save(emP);
    }

    @Override
    public EmojiPosts retrieveEmojiPostsById(Long id) {
        return emoPost.findById(id).orElse(null);
    }

    @Override
    public void remove(Long id) {
        emoPost.deleteById(id);
    }

    @Override
    public Map<String, Long> countEmojisByPostId(Long postId) {
        List<Object[]> results = emoPost.countEmojisByPostId(postId);
        Map<String, Long> counts = new HashMap<>();
        for (Object[] result : results) {
            String emoji = result[0].toString();
            Long count = (Long) result[1];
            counts.put(emoji, count);
        }
        return counts;
    }

    @Override
    public EmojiPosts reactToPost(Long postId,/* Long userId,*/ String email, Emojis newEmoji) {
        EmojiPosts existingReaction = emoPost.findByUserAndPost(email, postId);
        if (existingReaction != null) {
            existingReaction.setEmoji(newEmoji);
            existingReaction.setReactedAt(LocalDateTime.now());
            return emoPost.save(existingReaction);
        } else {
            EmojiPosts reaction = new EmojiPosts();
            reaction.setEmoji(newEmoji);
            reaction.setReactedAt(LocalDateTime.now());

            Posts post = postR.findById(postId).orElseThrow(() -> new RuntimeException("Post non trouvé avec l'ID : " + postId));
            reaction.setPost(post);


            User user = UserR.findByEmail(email).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + email));

            reaction.setUser(user);

            return emoPost.save(reaction);
        }
    }
}
