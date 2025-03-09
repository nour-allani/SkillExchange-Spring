package tn.esprit.skillexchange.Service.GestionUser;

import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.List;

public interface IUserService {
    List<User> retrieveUsers();
    User add(User user);
    User update(User user);
    User retrieveUserById(Long id);
    void remove(Long id);
}
