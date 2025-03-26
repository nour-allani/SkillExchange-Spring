package tn.esprit.skillexchange.Service.GestionUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionUser.Role;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepo userRepo;
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
                default:
                    break;
            }
        });

        return userRepo.save(user);
    }

}
