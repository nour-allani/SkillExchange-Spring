package tn.esprit.skillexchange.Service.GestionForumPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;

import java.util.List;

public interface IPostsService {
    List<Posts> retrievePost();
    Posts add(Posts p);
    Posts update(Posts p);
    Posts retrievePostsById(Long id);
    Page<Posts> retrieveApprovedPostPageable(Pageable pageable);
    void remove(Long id);
}
