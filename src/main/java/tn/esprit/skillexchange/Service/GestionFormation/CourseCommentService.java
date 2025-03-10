package tn.esprit.skillexchange.Service.GestionFormation;

import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.CourseComment;

import java.util.List;

public interface CourseCommentService {
    public List<CourseComment> retrieveAllComments();
    public CourseComment retrieveComment(Long commentId);
    public CourseComment addComment(CourseComment c);
    public void removeComment(Long commentId);
    public CourseComment modifyComment(CourseComment comment);
}
