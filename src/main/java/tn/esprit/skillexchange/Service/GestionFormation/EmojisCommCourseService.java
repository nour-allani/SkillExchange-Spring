package tn.esprit.skillexchange.Service.GestionFormation;

import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.EmojisCommentCourse;

import java.util.List;

public interface EmojisCommCourseService {
    public List<EmojisCommentCourse> retrieveAllEmojis();
    public EmojisCommentCourse retrieveEmojisCommentCourse(Long emoji);
    public EmojisCommentCourse addEmojiComment(EmojisCommentCourse e);
    public void removeEmojisCommentCourse(Long emojiComment);
    public EmojisCommentCourse modifyEmojiComment(EmojisCommentCourse emoji);
}
