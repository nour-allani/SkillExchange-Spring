package tn.esprit.skillexchange.Service.GestionFormation;

import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.RatingCourse;

import java.util.List;

public interface RatingCourseService {
    public List<RatingCourse> retrieveAllRatings();
    public RatingCourse retrieveRating(Long ratingId);
    public RatingCourse addRating(RatingCourse r);
    public void removeRating(Long ratingId);
    public RatingCourse modifyRating(RatingCourse rating);
    double getAverageRatingForCourse(Long courseId); // New method
    long getRatingCountForCourse(Long courseId); // New method
}
