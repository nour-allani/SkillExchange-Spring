package tn.esprit.skillexchange.Service.GestionFormation;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.EmojisCommentCourse;
import tn.esprit.skillexchange.Repository.GestionFormation.EmojisCommentCourseRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class EmojisCommCourseImpl implements EmojisCommCourseService{

    @Autowired
    EmojisCommentCourseRepo emojisCommentCourseRepo ;

    @Override
    public List<EmojisCommentCourse> retrieveAllEmojis() {
        return emojisCommentCourseRepo.findAll();
    }

    @Override
    public EmojisCommentCourse retrieveEmojisCommentCourse(Long emoji) {
        return emojisCommentCourseRepo.findById(emoji).get();
    }

    @Override
    public EmojisCommentCourse addEmojiComment(EmojisCommentCourse e) {
        return emojisCommentCourseRepo.save(e);
    }

    @Override
    public void removeEmojisCommentCourse(Long emojiComment) {
        emojisCommentCourseRepo.deleteById(emojiComment);
    }

    @Override
    public EmojisCommentCourse modifyEmojiComment(EmojisCommentCourse emoji) {
        return emojisCommentCourseRepo.save(emoji);
    }
}
