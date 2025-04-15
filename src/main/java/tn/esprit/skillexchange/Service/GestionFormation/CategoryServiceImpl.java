package tn.esprit.skillexchange.Service.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Repository.GestionFormation.CategoryRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo catRepo ;

    @Override
    public List<Category> retrieveAllCategories() {
        //return catRepo.findAll();
        List<Category> categories = catRepo.findAll(); // ou .getCategory()
        List<Object[]> counts = catRepo.getCourseCountPerCategory();

        Map<Long, Long> countMap = counts.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));

        for (Category cat : categories) {
            cat.setCourseCount(countMap.getOrDefault(cat.getId(), 0L));
        }
        return categories;


    }
//    @Override
//    public List<Category> retrieveAllCategories() {
//        // Récupère toutes les catégories depuis la base de données
//        List<Category> categories = catRepo.findAll(); // équivalent à getCategory()
//
//        // Appelle une méthode personnalisée du repository qui retourne une liste d'objets [categoryId, nombreDeCours]
//        // Exemple de résultat : [[1, 5], [2, 8], [4, 0], ...]
//        List<Object[]> counts = catRepo.getCourseCountPerCategory();
//
//        // Transforme cette liste en Map où :
//        //    - la clé est l'ID de la catégorie
//        //    - la valeur est le nombre de cours associés
//        Map<Long, Long> countMap = counts.stream()
//                .collect(Collectors.toMap(
//                        row -> (Long) row[0],  // clé : category_id
//                        row -> (Long) row[1]   // valeur : count
//                ));
//
//        // Pour chaque catégorie récupérée
//        for (Category cat : categories) {
//            // On ajoute dynamiquement le nombre de cours correspondant
//            // Si l'ID de la catégorie n'est pas dans le map (aucun cours), on met 0 par défaut
//            cat.setCourseCount(countMap.getOrDefault(cat.getId(), 0L));
//        }
//
//        // Retourne la liste enrichie des catégories avec leur nombre de cours
//        return categories;
//    }


    @Override
    public Category retrieveCategory(Long categoryId) {
        return catRepo.findById(categoryId).get();
    }

    @Override
    public Category addCategory(Category g) {
        g.setDate_ajout(java.sql.Date.valueOf(LocalDate.now()));

        return catRepo.save(g);
    }

    @Override
    public List<Courses> getCoursesByCategorieId(int id) {
        return catRepo.findCoursesByCategorieId(id);
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
