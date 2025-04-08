package tn.esprit.skillexchange.Controller.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Service.GestionFormation.CategoryService;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;


    @GetMapping("/retrieve-all-categories")
    public List<Category> getCategories() {
        List<Category> listCategories = categoryService.retrieveAllCategories();
        return listCategories;
    }
    @PostMapping("findById/")
    public List<Courses> getCoursesByCategorie(@RequestBody  int id) {
        //TODO: process POST request
        System.out.println("test takwa"+ id );
        return categoryService.getCoursesByCategorieId(id) ;	}


    @GetMapping("/retrieve-category/{category-id}")
    public Category retrieveCategory(@PathVariable("category-id") Long catId) {
        Category category = categoryService.retrieveCategory(catId);
        return category;
    }

    @PostMapping("/add-category")
    public Category addCategory(@RequestBody Category g) {
        Category category = categoryService.addCategory(g);
        category.setDate_ajout(java.sql.Date.valueOf(LocalDate.now()));
        return category;
    }

     @DeleteMapping("/remove-category/{category-id}")
    public void removeCategory(@PathVariable("category-id") Long catId) {
        categoryService.removeCategory(catId);
    }



    @PutMapping("/modify-category")
    public Category modifyChambre(@RequestBody Category c) {
        Category category = categoryService.modifyCategory(c);
        return category;
    }


}
