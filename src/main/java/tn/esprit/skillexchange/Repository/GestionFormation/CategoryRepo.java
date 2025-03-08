package tn.esprit.skillexchange.Repository.GestionFormation;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.skillexchange.Entity.GestionFormation.Category;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}
