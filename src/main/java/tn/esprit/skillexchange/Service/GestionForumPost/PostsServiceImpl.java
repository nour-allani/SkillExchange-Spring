package tn.esprit.skillexchange.Service.GestionForumPost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import tn.esprit.skillexchange.Repository.GestionForumPost.PostsRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j

public class PostsServiceImpl implements IPostsService{
    PostsRepo post;
    @Override
    public List<Posts> retrievePost() {
        return post.findAll();
    }

    @Override
    public Posts add(Posts p) {
        return post.save(p);
    }

    @Override
    public Posts update(Posts p) {
        return post.save(p);
    }

    @Override
    public Posts retrievePostsById(Long id) {
        return post.findById(id).get();
    }

    @Override
    public void remove(Long id) {
post.deleteById(id);
    }
}
