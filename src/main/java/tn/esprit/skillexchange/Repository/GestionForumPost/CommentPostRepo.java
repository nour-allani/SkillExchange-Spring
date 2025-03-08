package tn.esprit.skillexchange.Repository.GestionForumPost;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;

public interface CommentPostRepo extends JpaRepository<CommentPost,Long> {
}
