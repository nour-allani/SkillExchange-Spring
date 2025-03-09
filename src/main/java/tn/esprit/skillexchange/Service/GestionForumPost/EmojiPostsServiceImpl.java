package tn.esprit.skillexchange.Service.GestionForumPost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;
import tn.esprit.skillexchange.Repository.GestionForumPost.EmojiPostsRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EmojiPostsServiceImpl implements IEmojiPostsService {
    EmojiPostsRepo emoPost;
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
        return emoPost.findById(id).get();
    }

    @Override
    public void remove(Long id) {
    emoPost.deleteById(id);
    }
}
