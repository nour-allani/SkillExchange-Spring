package tn.esprit.skillexchange.Service.GestionUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;

import java.util.List;

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
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
