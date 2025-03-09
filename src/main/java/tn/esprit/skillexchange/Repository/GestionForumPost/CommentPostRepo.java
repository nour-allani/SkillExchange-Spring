package tn.esprit.skillexchange.Repository.GestionForumPost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;
@Repository

public interface CommentPostRepo extends JpaRepository<CommentPost,Long> {
}
