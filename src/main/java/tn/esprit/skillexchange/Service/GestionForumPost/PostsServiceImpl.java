package tn.esprit.skillexchange.Service.GestionForumPost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionForumPost.ImagePost;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionForumPost.PostsRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PostsServiceImpl implements IPostsService {

    private final PostsRepo postRepo;
    private final UserRepo userRepo;

    @Override
    public List<Posts> retrievePost() {
        return postRepo.findAll();
    }

    public Page<Posts> retrieveApprovedPostPageable(Pageable pageable) {
        return postRepo.findByApprovedTrue(pageable);  // Filtrer les posts approuvés
    }


    @Override
    public Posts add(Posts p) {
        User user = userRepo.findById(p.getUser().getId()).orElse(null);
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé !");
        }

        p.setUser(user);
        p.setCreatedAt(new Date());
        p.setUpdatedAt(new Date());

        if (p.getImagePost() != null) {
            for (ImagePost imgP : p.getImagePost()) {
                imgP.setPost(p);
            }
        }

        return postRepo.save(p);
    }

    @Override
    public Posts update(Posts p) {
       /* p.setUpdatedAt(new Date());
        return postRepo.save(p);*/

            if (p.getImagePost() != null) {
                for (ImagePost img : p.getImagePost()) {
                    img.setPost(p);  // Associer chaque image au post
                }
            }

            // Sauvegarder et renvoyer le post mis à jour
            return postRepo.save(p);


    }

    @Override
    public Posts retrievePostsById(Long id) {
        return postRepo.findById(id).orElse(null);
    }

    @Override
    public void remove(Long id) {
        postRepo.deleteById(id);
    }

}
