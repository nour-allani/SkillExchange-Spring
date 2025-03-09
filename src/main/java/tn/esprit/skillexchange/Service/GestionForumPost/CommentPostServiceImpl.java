package tn.esprit.skillexchange.Service.GestionForumPost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;
import tn.esprit.skillexchange.Repository.GestionForumPost.CommentPostRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CommentPostServiceImpl implements ICommentPostService{
    CommentPostRepo comPost;
    @Override
    public List<CommentPost> retrieveCommentPosts() {
        return comPost.findAll();
    }

    @Override
    public CommentPost add(CommentPost comP) {
        return comPost.save(comP);
    }

    @Override
    public CommentPost update(CommentPost comP) {
        return comPost.save(comP);
    }

    @Override
    public CommentPost retrieveCommentPostById(Long id) {
        return comPost.findById(id).get();
    }

    @Override
    public void remove(Long id) {
        comPost.deleteById(id);

    }
}
