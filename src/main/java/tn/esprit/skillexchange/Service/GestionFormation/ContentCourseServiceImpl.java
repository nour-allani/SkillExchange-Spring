package tn.esprit.skillexchange.Service.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.skillexchange.Entity.GestionFormation.CourseContent;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Repository.GestionFormation.CourseContentRepo;
import tn.esprit.skillexchange.Repository.GestionFormation.CourseRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ContentCourseServiceImpl implements ContentCourseService {
    @Autowired
    private CourseContentRepo contentRepo ;
    @Autowired
    private CourseRepo courseRepo ;

    @Override
    public List<CourseContent> retrieveAllContents() {
        return contentRepo.findAll();
    }

    @Override
    public CourseContent retrieveCourseContent(int courseContentId) {
        return contentRepo.findById(courseContentId).get();
    }

    @Override
    public CourseContent addContent(CourseContent c) {
        return contentRepo.save(c);
    }

    @Override
    public void removeCourseContent(int courseId) {
        contentRepo.deleteById(courseId);
    }

    @Override
    public CourseContent modifyCourseContent(CourseContent courseC) {
        return contentRepo.save(courseC);
    }

    @Override
    public List<CourseContent> getContentsByCourseId(int id) {
        return contentRepo.getCoursesByUserId(id);
    }
}
