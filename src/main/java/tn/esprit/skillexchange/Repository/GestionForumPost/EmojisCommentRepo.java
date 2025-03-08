package tn.esprit.skillexchange.Repository.GestionForumPost;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiComments;

public interface EmojisCommentRepo extends JpaRepository<EmojiComments,Long> {
}
