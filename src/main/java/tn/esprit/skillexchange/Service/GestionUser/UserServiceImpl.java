package tn.esprit.skillexchange.Service.GestionUser;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionUser.Banned;
import tn.esprit.skillexchange.Entity.GestionUser.Role;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionUser.BannedRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BannedRepo bannedRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> retrieveUsers() {
        return userRepo.findAll();
    }

    @Override
    public User add(User user) {
        return userRepo.save(user);
    }

    @Override
    public User update(User user) {
        return userRepo.findById(user.getId())
                .map(existing -> userRepo.save(user))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User retrieveUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User retrieveUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void remove(Long id) {
        userRepo.deleteById(id);
    }


    @Override
    public UserDetailsService userDetailsService () {

        return username -> userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for username "+username));
    }

    @Override
    public User updateUserPartially(Long id, Map<String, Object> updates) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    user.setName((String) value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    break;
                case "password":
                    user.setPassword((String) value);
                    break;
                case "role":
                    user.setRole(Role.valueOf((String) value));
                    break;
                case "verified":
                    user.setVerified((Boolean) value);
                    break;
                case "image":
                    user.setImage((String) value);
                    break;
                case "balance":
                    if (value instanceof Integer) {
                        user.setBalance(((Integer) value).floatValue());
                    } else if (value instanceof Float) {
                        user.setBalance((Float) value);
                    }
                    break;
                case "signature":
                    user.setSignature((String) value);
                    break;
                case "bio":
                    user.setBio((String) value);
                    break;
                case "facebook":
                    user.setFacebook((String) value);
                    break;
                case "github":
                    user.setGithub((String) value);
                    break;
                case "linkedin":
                    user.setLinkedin((String) value);
                    break;
                default:
                    break;
            }
        });

        return userRepo.save(user);
    }

    @Override
    public User updateUserImage(Long userId, String imageBase64) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setImage(imageBase64);
        return userRepo.save(user);
    }

    @Override
    public void changePassword(String email, String currentPassword, String newPassword) {
        User user = retrieveUserByEmail(email);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    @Override
    public void banUser(Long userId, String reason, Date endDate, Long bannedBy) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User to ban not found"));

        userRepo.findById(bannedBy)
                .orElseThrow(() -> new EntityNotFoundException("User that will ban not found"));

        if (user.getBan() != null) {
            throw new IllegalStateException("User is already banned");
        }

        Banned ban = new Banned();
        ban.setReason(reason);
        ban.setEndDate(endDate);
        ban.setBannedBy(bannedBy);
        ban.setUser(user);

        bannedRepository.save(ban);
        user.setBan(ban);
        userRepo.save(user);
    }

    @Override
    public void unbanUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.getBan() == null) {
            throw new IllegalStateException("User is not banned");
        }

        Banned ban = user.getBan();
        user.setBan(null);
        userRepo.save(user);

        bannedRepository.delete(ban);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
