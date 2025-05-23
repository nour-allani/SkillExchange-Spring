package tn.esprit.skillexchange.Service.GestionUser;

import org.springframework.security.core.userdetails.UserDetailsService;
import tn.esprit.skillexchange.Entity.GestionUser.Badge;
import tn.esprit.skillexchange.Entity.GestionUser.HistoricTransactions;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Entity.GestionUser.UserStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IUserService {
    List<User> retrieveUsers();
    User add(User user);
    User update(User user);
    User retrieveUserById(Long id);
    User retrieveUserByEmail(String email);
    void remove(Long id);

    UserDetailsService userDetailsService();

    User updateUserPartially(Long id, Map<String,Object> updates);

    User updateUserImage(Long id, String base64Image);

    void changePassword(String email, String currentPassword, String newPassword);

    void resetPassword(String email, String newPassword);

    void banUser(Long id, String reason, Date endDate, Long bannedBy);

    void unbanUser(Long id);

    void assignBadgeToUser(Long userId, Long badgeId);

    void removeBadgeFromUser(Long userId, Long badgeId);

    Set<Badge> getUserBadges(Long userId);

    HistoricTransactions addTransactionToUser(Long userId, HistoricTransactions transaction);

    List<HistoricTransactions> getUserTransactions(Long userId);

    void changeUserStatus(String email, UserStatus status);
}
