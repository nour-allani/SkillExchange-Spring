package tn.esprit.skillexchange.Repository.GestionForumPost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.Emojis;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiPosts;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.List;

@Repository
public interface EmojiPostsRepo extends JpaRepository<EmojiPosts, Long> {

    @Query("SELECT e.emoji, COUNT(e) FROM EmojiPosts e WHERE e.post.idPost = :postId GROUP BY e.emoji")
    java.util.List<Object[]> countEmojisByPostId(@Param("postId") Long postId);

    /*@Query("SELECT e FROM EmojiPosts e WHERE e.user.id = :userId AND e.post.idPost = :postId")
    EmojiPosts findByUserAndPost(@Param("userId") Long userId, @Param("postId") Long postId);*/
    @Query("SELECT e FROM EmojiPosts e WHERE e.user.email = :email AND e.post.idPost = :postId")
    EmojiPosts findByUserAndPost(@Param("email") String email, @Param("postId") Long postId);
    @Query("SELECT e.user FROM EmojiPosts e WHERE e.post.idPost = :postId AND e.emoji = :emoji")
    List<User> findUsersByEmojiAndPostId(@Param("postId") Long postId, @Param("emoji") Emojis emoji);

}
