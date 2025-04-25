package tn.esprit.skillexchange.Service.GestionForumPost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;
import tn.esprit.skillexchange.Repository.GestionForumPost.CommentPostRepo;
import tn.esprit.skillexchange.Repository.GestionForumPost.PostsRepo;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.Mailing.GmailService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class CommentPostServiceImpl implements ICommentPostService {

    private final CommentPostRepo comPost;
    private final PostsRepo post;
    private final UserRepo userRepo;
    private final GmailService gmailService;


    @Override
    public List<CommentPost> retrieveCommentPosts() {
        return comPost.findAll();
    }

  /*  @Override
    public CommentPost add(CommentPost comP) {
        // Extraire les usernames mentionnés avec @
        Set<String> mentionedUsernames = extractMentionedUsernames(comP.getContent());

        for (String username : mentionedUsernames) {
            User mentionedUser = userRepo.findByName(username); // Assure-toi que cette méthode existe dans UserRepo
            if (mentionedUser != null) {
                log.info("Mention detected: " + mentionedUser.getName());
                try {
                    gmailService.sendMentionNotification(
                            mentionedUser.getEmail(),
                            comP.getEmail(), // Email de l'auteur du commentaire (ajuste selon ta structure)
                            comP.getContent()
                    );
                } catch (Exception e) {
                    log.error("Failed to send mention notification to " + mentionedUser.getEmail(), e);
                }
            } else {
                log.warn("Mentioned user not found: " + username);
            }
        }

        return comPost.save(comP);
    }
*/
    private Set<String> extractMentionedUsernames(String content) {
        Set<String> usernames = new HashSet<>();
        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            usernames.add(matcher.group(1)); // récupère le nom sans le '@'
        }
        return usernames;
    }
    @Override
    public CommentPost add(CommentPost comP) {
        // Extraire les usernames mentionnés avec @
        Set<String> mentionedUsernames = extractMentionedUsernames(comP.getContent());

        if (mentionedUsernames.contains("everyone")) {  // Vérifier si @everyone est mentionné
            log.info("@everyone mention detected, sending email to all users.");

            // Récupérer tous les utilisateurs
            List<User> allUsers = userRepo.findAll(); // Assure-toi que la méthode findAll existe dans ton UserRepo

            // Envoyer un email à chaque utilisateur
            for (User user : allUsers) {
                try {
                    gmailService.sendMentionNotification(
                            user.getEmail(),
                            comP.getEmail(),  // Email de l'auteur du commentaire
                            comP.getContent()
                    );
                    log.info("Notification sent successfully to: " + user.getEmail());
                } catch (Exception e) {
                    log.error("Failed to send mention notification to " + user.getEmail(), e);
                }
            }
        } else {
            // Si @everyone n'est pas mentionné, envoyer des emails aux utilisateurs mentionnés individuellement
            for (String username : mentionedUsernames) {
                User mentionedUser = userRepo.findByName(username);
                if (mentionedUser != null) {
                    log.info("Mention detected: " + mentionedUser.getName());
                    try {
                        gmailService.sendMentionNotification(
                                mentionedUser.getEmail(),
                                comP.getEmail(),
                                comP.getContent()
                        );
                        log.info("Notification sent successfully to: " + mentionedUser.getEmail());
                    } catch (Exception e) {
                        log.error("Failed to send mention notification to " + mentionedUser.getEmail(), e);
                    }
                } else {
                    log.warn("Mentioned user not found: " + username);
                }
            }
        }

        return comPost.save(comP);
    }


    @Override
    public CommentPost update(CommentPost comP) {
        return comPost.save(comP);
    }

    @Override
    public List<CommentPost> retrieveCommentPostsByPostId(Long postId) {
        return comPost.findByPostIdPost(postId);
    }

    @Override
    public void remove(Long id) {
        comPost.deleteById(id);
    }
}
