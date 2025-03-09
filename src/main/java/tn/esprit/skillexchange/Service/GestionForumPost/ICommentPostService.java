package tn.esprit.skillexchange.Service.GestionForumPost;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;

import java.util.List;

public interface ICommentPostService {
    List<CommentPost> retrieveCommentPosts();
    CommentPost add(CommentPost banned);
    CommentPost update(CommentPost banned);
    CommentPost retrieveCommentPostById(Long id);
    void remove(Long id);
}