package tn.esprit.skillexchange.Service.GestionFormation;

import tn.esprit.skillexchange.Entity.GestionFormation.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> retrieveAllCategories();
    public Category retrieveCategory(Long categoryId);
    public Category addCategory(Category g);
    public void removeCategory(Long categoryId);
    public Category modifyCategory(Category gategory);
}
