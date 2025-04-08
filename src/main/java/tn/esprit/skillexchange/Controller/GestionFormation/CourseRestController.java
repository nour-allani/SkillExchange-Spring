package tn.esprit.skillexchange.Controller.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionFormation.Category;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Service.GestionFormation.CategoryService;
import tn.esprit.skillexchange.Service.GestionFormation.FormationService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/courses")
public class CourseRestController {
    @Autowired
    FormationService courseService;


    @GetMapping("/retrieve-all-courses")
    public List<Courses> getCourses() {
        List<Courses> listCourses = courseService.retrieveAllCourses();
        return listCourses;
    }

    @GetMapping("/retrieve-course/{course-id}")
    public Courses retrieveCourse(@PathVariable("course-id") Long courseId) {
        Courses course = courseService.retrieveCourse(courseId);
        return course;
    }

    @PostMapping("/add-course")
    public Courses addCourses(@RequestBody Courses c) {
        Courses course = courseService.addCourse(c);
        return course;
    }

    @DeleteMapping("/remove-course/{course-id}")
    public void removeCourse(@PathVariable("course-id") Long courseId) {
        courseService.removeCourse(courseId);
    }



    @PutMapping("/modify-course")
    public Courses modifyCourse(@RequestBody Courses c) {
        Courses course = courseService.modifyCourse(c);
        return course;
    }

    @PostMapping("findById/")
    public List<Courses> getCoursesByUserId(@RequestBody  int id) {
        System.out.println("test user"+ id );
        return courseService.getCoursesByUserId(id) ;	}


}
