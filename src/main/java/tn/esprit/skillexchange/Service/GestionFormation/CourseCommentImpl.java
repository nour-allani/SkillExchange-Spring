package tn.esprit.skillexchange.Service.GestionFormation;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.CourseComment;
import tn.esprit.skillexchange.Repository.GestionFormation.CourseCommentRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseCommentImpl implements CourseCommentService {

    @Autowired
    CourseCommentRepo courseCommentRepo ;
    @Override
    public List<CourseComment> retrieveAllComments() {
        return courseCommentRepo.findAll();
    }

    @Override
    public CourseComment retrieveComment(Long commentId) {
        return courseCommentRepo.findById(commentId).get();
    }

    @Override
    public CourseComment addComment(CourseComment c) {
        return courseCommentRepo.save(c);
    }

    @Override
    public void removeComment(Long commentId) {
        courseCommentRepo.deleteById(commentId);
    }

    @Override
    public CourseComment modifyComment(CourseComment comment) {
        return courseCommentRepo.save(comment);
    }
}
