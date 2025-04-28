package tn.esprit.skillexchange.Service.GestionForumPost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionForumPost.CommentPost;
import tn.esprit.skillexchange.Entity.GestionForumPost.Posts;
import tn.esprit.skillexchange.Repository.GestionForumPost.CommentPostRepo;
import tn.esprit.skillexchange.Repository.GestionForumPost.PostsRepo;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.Mailing.GmailService;

import java.util.Date;
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
    private final PostsRepo postRepo;
    private final UserRepo userRepo;
    private final GmailService gmailService;
    private final HuggingFaceService huggingFaceService;


    @Override
    public List<CommentPost> retrieveCommentPosts() {
        return comPost.findAll();
    }

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
        // Récupérer le contenu du post associé au commentaire
        Posts post = postRepo.findById(comP.getPost().getIdPost()).orElse(null);

        if (post != null && (comP.getContent() == null || comP.getContent().trim().isEmpty())) {
            // Générer un commentaire basé sur le contenu du post uniquement si le commentaire est vide
            String generatedComment = huggingFaceService.generateComment(post.getContent()).block(); // <<< BLOCK ici pour attendre
            if (generatedComment != null && !generatedComment.trim().isEmpty()) {
                comP.setContent(generatedComment);
                log.info("Generated comment: " + generatedComment);
            } else {
                log.warn("No comment generated.");
            }
        } else {
            log.warn("Post content is empty, or comment already provided.");
        }

        // Extraire les usernames mentionnés avec @
        Set<String> mentionedUsernames = extractMentionedUsernames(comP.getContent());

        if (mentionedUsernames.contains("everyone")) {
            log.info("@everyone mention detected, sending email to all users.");

            List<User> allUsers = userRepo.findAll();
            for (User user : allUsers) {
                try {
                    gmailService.sendMentionNotification(
                            user.getEmail(),
                            comP.getEmail(),
                            comP.getContent()
                    );
                    log.info("Notification sent successfully to: " + user.getEmail());
                } catch (Exception e) {
                    log.error("Failed to send mention notification to " + user.getEmail(), e);
                }
            }
        } else {
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
    public List<CommentPost> retrieveCommentPostsByPostId(Long postId) {
        return comPost.findByPostIdPost(postId);
    }

    @Override
    public void remove(Long id) {
        comPost.deleteById(id);
    }


    @Override
    public CommentPost update(CommentPost comP) {

        CommentPost existingReview = comPost.findById(comP.getIdComment()).orElse(null);

        if (existingReview == null) {

            return null;
        }

        // Mise à jour des informations de la revue
        if (comP.getContent() != null) {
            existingReview.setContent(comP.getContent());
        }



        existingReview.setUpdatedAt(new Date());


        return comPost.save(existingReview);
    }
}