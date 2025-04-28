package tn.esprit.skillexchange.Service.GestionFormation;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.skillexchange.Entity.GestionFormation.CourseContent;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;

import java.io.IOException;
import java.util.List;

public interface ContentCourseService {

    public List<CourseContent> retrieveAllContents();
    public CourseContent retrieveCourseContent(int courseContentId);
    public CourseContent addContent(CourseContent c);
    public void removeCourseContent(int courseId);
    public CourseContent modifyCourseContent(CourseContent courseC);
    public List<CourseContent> getContentsByCourseId(int id) ;


    }
