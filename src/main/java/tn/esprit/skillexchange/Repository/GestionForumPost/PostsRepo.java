package tn.esprit.skillexchange.Repository.GestionForumPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;


@Repository
public interface PostsRepo extends JpaRepository<Posts, Long> {
    Page<Posts> findByApprovedTrue(Pageable pageable);
}
