package tn.esprit.skillexchange.Controller.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.CourseComment;
import tn.esprit.skillexchange.Service.GestionFormation.CourseCommentService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/courseComment")
public class CourseCommentRestController {

    @Autowired
    CourseCommentService courseCommentService ;


    @GetMapping("/retrieve-all-comments")
    public List<CourseComment> getComments() {
        List<CourseComment> listComments = courseCommentService.retrieveAllComments();
        return listComments;
    }

    @GetMapping("/retrieve-comment/{comment-id}")
    public CourseComment retrieveComment(@PathVariable("comment-id") Long commentId) {
        CourseComment comment = courseCommentService.retrieveComment(commentId);
        return comment;
    }

    @PostMapping("/add-comment")
    public CourseComment addComment(@RequestBody CourseComment c) {
        CourseComment comment = courseCommentService.addComment(c);
        return comment;
    }

        @DeleteMapping("/remove-comment/{comment-id}")
    public void removeComment(@PathVariable("comment-id") Long commentId) {
        courseCommentService.removeComment(commentId);
    }

    @PutMapping("/modify-courseComment")
    public CourseComment modifyCourseComment(@RequestBody CourseComment c) {
        CourseComment comment = courseCommentService.modifyComment(c);
        return comment;
    }

}
