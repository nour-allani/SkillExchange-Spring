package tn.esprit.skillexchange.Repository.GestionUser;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionUser.Role;
import tn.esprit.skillexchange.Entity.GestionUser.User;

import java.util.Date;
import java.util.Optional;
import tn.esprit.skillexchange.Entity.GestionUser.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    User findByRole(Role role);

}
