package tn.esprit.skillexchange.Repository.GestionForumPost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiComments;
@Repository

@Repository
public interface EmojisCommentRepo extends JpaRepository<EmojiComments,Long> {
}
