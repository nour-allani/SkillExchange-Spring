package tn.esprit.skillexchange.Controller.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.RatingCourse;
import tn.esprit.skillexchange.Service.GestionFormation.CategoryService;
import tn.esprit.skillexchange.Service.GestionFormation.RatingCourseService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ratingCourse")
public class RatingCourseRestController {

    @Autowired
    RatingCourseService ratingCourseService;


    @GetMapping("/retrieve-all-rating")
    public List<RatingCourse> getRating() {
        List<RatingCourse> listRating = ratingCourseService.retrieveAllRatings();
        return listRating;
    }

    @GetMapping("/retrieve-rating/{rating-id}")
    public RatingCourse retrieveRating(@PathVariable("rating-id") Long ratingId) {
        RatingCourse rating = ratingCourseService.retrieveRating(ratingId);
        return rating;
    }

    @PostMapping("/add-rating")
    public RatingCourse addRating(@RequestBody RatingCourse g) {
        RatingCourse rating = ratingCourseService.addRating(g);
        return rating;
    }

    @DeleteMapping("/remove-rating/{rating-id}")
    public void removeRating(@PathVariable("rating-id") Long ratingId) {
        ratingCourseService.removeRating(ratingId);
    }



    @PutMapping("/modify-rating")
    public RatingCourse modifyRating(@RequestBody RatingCourse c) {
        RatingCourse rating = ratingCourseService.modifyRating(c);
        return rating;
    }



}
