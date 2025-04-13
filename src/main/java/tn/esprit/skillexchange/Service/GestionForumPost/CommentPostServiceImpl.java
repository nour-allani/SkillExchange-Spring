package tn.esprit.skillexchange.Service.GestionForumPost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;
import tn.esprit.skillexchange.Repository.GestionForumPost.CommentPostRepo;
import tn.esprit.skillexchange.Repository.GestionForumPost.PostsRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CommentPostServiceImpl implements ICommentPostService {

    private final CommentPostRepo comPost;
    private final PostsRepo post;

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
    public List<CommentPost> retrieveCommentPostsByPostId(Long postId) {
        return comPost.findByPostIdPost(postId);
    }

    @Override
    public void remove(Long id) {
        comPost.deleteById(id);
    }
}
