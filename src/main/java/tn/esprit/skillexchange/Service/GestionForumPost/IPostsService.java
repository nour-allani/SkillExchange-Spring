package tn.esprit.skillexchange.Service.GestionForumPost;

import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import java.util.List;

public interface IPostsService {
    List<Posts> retrievePost();
    Posts add(Posts p);
    Posts update(Posts p);
    Posts retrievePostsById(Long id);
    void remove(Long id);
}
