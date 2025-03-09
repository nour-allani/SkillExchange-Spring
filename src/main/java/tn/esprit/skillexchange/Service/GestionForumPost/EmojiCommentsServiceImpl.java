package tn.esprit.skillexchange.Service.GestionForumPost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiComments;
import tn.esprit.skillexchange.Repository.GestionForumPost.EmojisCommentRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EmojiCommentsServiceImpl implements IEmojiCommentsService{
    EmojisCommentRepo emoCom;
    @Override
    public List<EmojiComments> retrieveEmojiCommentss() {
        return emoCom.findAll();
    }

    @Override
    public EmojiComments add(EmojiComments emC) {
        return emoCom.save(emC);
    }

    @Override
    public EmojiComments update(EmojiComments emC) {
        return emoCom.save(emC);
    }

    @Override
    public EmojiComments retrieveEmojiCommentsById(Long id) {
        return emoCom.findById(id).get();
    }

    @Override
    public void remove(Long id) {
        emoCom.deleteById(id);

    }
}
