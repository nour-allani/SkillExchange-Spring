package tn.esprit.skillexchange.Service.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Repository.GestionFormation.CategoryRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo catRepo ;

    @Override
    public List<Category> retrieveAllCategories() {
        return catRepo.findAll();
    }

    @Override
    public Category retrieveCategory(Long categoryId) {
        return catRepo.findById(categoryId).get();
    }

    @Override
    public Category addCategory(Category g) {
        return catRepo.save(g);
    }

    @Override
    public void removeCategory(Long categoryId) {
        catRepo.deleteById(categoryId);
    }

    @Override
    public Category modifyCategory(Category gategory) {
        return catRepo.save(gategory);
    }
}
