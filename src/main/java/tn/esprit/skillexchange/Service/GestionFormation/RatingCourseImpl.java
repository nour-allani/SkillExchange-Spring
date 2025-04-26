package tn.esprit.skillexchange.Service.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.RatingCourse;
import tn.esprit.skillexchange.Repository.GestionFormation.RatingCourseRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RatingCourseImpl implements RatingCourseService {

    @Autowired
    RatingCourseRepo ratingCourseRepo ;
    @Override
    public List<RatingCourse> retrieveAllRatings() {
        return ratingCourseRepo.findAll();
    }

    @Override
    public RatingCourse retrieveRating(Long ratingId) {
        return ratingCourseRepo.findById(ratingId).get();
    }

    @Override
    public RatingCourse addRating(RatingCourse r) {
        return ratingCourseRepo.save(r);
    }

    @Override
    public void removeRating(Long ratingId) {
       ratingCourseRepo.deleteById(ratingId);
    }

    @Override
    public RatingCourse modifyRating(RatingCourse rating) {
        return ratingCourseRepo.save(rating);
    }

    @Override
    public double getAverageRatingForCourse(Long courseId) {
        Double average = ratingCourseRepo.findAverageRatingByCourseId(courseId.intValue());
        return average != null ? average : 0.0;
    }

    @Override
    public long getRatingCountForCourse(Long courseId) {
        return ratingCourseRepo.countByCourseId(courseId.intValue());
    }
}
