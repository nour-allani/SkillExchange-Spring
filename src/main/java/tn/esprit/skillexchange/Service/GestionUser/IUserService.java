package tn.esprit.skillexchange.Service.GestionUser;

import org.springframework.security.core.userdetails.UserDetailsService;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    List<User> retrieveUsers();
    User add(User user);
    User update(User user);
    User retrieveUserById(Long id);
    User retrieveUserByEmail(String email);
    void remove(Long id);

    UserDetailsService userDetailsService();

    User updateUserPartially(Long id, Map<String,Object> updates);
}
