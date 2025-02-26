package tn.esprit.skillexchange.Repository.GestionForumPost;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;

public interface EmojiPostsRepo extends JpaRepository<EmojiPosts,Long> {
}
