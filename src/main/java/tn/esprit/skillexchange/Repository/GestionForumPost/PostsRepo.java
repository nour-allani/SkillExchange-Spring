package tn.esprit.skillexchange.Repository.GestionForumPost;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;

public interface PostsRepo extends JpaRepository<Posts,Long> {
}
