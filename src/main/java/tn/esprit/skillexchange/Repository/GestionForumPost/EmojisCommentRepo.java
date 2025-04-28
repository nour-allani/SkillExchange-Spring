package tn.esprit.skillexchange.Repository.GestionForumPost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.Emojis;
import tn.esprit.skillexchange.Entity.GestionForumPost.EmojiComments;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.List;

@Repository
public interface EmojisCommentRepo extends JpaRepository<EmojiComments, Long> {

    void deleteByCommentPost_IdCommentAndEmailAndEmoji(Long idComment, String email, Emojis emoji);

    boolean existsByCommentPost_IdCommentAndEmailAndEmoji(Long idComment, String email, Emojis emoji);

    @Query("SELECT ec FROM EmojiComments ec WHERE ec.user.email = :email AND ec.commentPost.idComment = :commentId")
    EmojiComments findByUserAndComment(@Param("email") String email, @Param("commentId") Long commentId);

    @Query("SELECT ec.emoji, COUNT(ec) FROM EmojiComments ec WHERE ec.commentPost.idComment = :commentId GROUP BY ec.emoji")
    List<Object[]> countEmojisByCommentId(@Param("commentId") Long commentId);

    @Query("SELECT ec.email FROM EmojiComments ec WHERE ec.commentPost.idComment = :commentId AND ec.emoji = :emoji")
    List<User> findByUserAndComment(@Param("commentId") Long commentId, @Param("emoji") Emojis emoji);

}
