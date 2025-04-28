package tn.esprit.skillexchange.Controller.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.skillexchange.Entity.GestionFormation.CourseContent;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Service.GestionFormation.ContentCourseService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/courseContent")
public class CourseContentController {
    @Autowired
    private ContentCourseService contentService;


    @GetMapping("/retrieve-all-contents")
    public List<CourseContent> getContents() {
        List<CourseContent> listcontents = contentService.retrieveAllContents();
        return listcontents;
    }

    @GetMapping("/retrieve-content/{content-id}")
    public CourseContent retrieveCourseContent(@PathVariable("content-id") int courseId) {
        CourseContent courseContent = contentService.retrieveCourseContent(courseId);
        return courseContent;
    }

    @PostMapping("/add-content")
    public CourseContent addContent(@RequestBody CourseContent c) {
        CourseContent courseContent = contentService.addContent(c);
        return courseContent;
    }

    @DeleteMapping("/remove-content/{content-id}")
    public void removeCourse(@PathVariable("content-id") int courseId) {
        contentService.removeCourseContent(courseId);
    }



    @PutMapping("/modify-Content")
    public CourseContent modifyCourse(@RequestBody CourseContent c) {
        CourseContent courseContent = contentService.modifyCourseContent(c);
        return courseContent;
    }

    @PostMapping("findById/")
    public List<CourseContent> getContentByCourseId(@RequestBody  int id) {
        return contentService.getContentsByCourseId(id) ;
    }
}
