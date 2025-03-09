package tn.esprit.skillexchange.Repository.GestionUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.skillexchange.Entity.GestionUser.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
}
