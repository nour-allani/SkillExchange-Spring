package tn.esprit.skillexchange.Repository.GestionForumPost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;

import java.util.List;

@Repository
public interface CommentPostRepo extends JpaRepository<CommentPost,Long> {
    List<CommentPost> findByPostIdPost(Long postId);
}
