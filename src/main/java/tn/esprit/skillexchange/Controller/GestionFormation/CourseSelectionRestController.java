package tn.esprit.skillexchange.Controller.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionFormation.CourseContent;
import tn.esprit.skillexchange.Entity.GestionFormation.UserCourseContentSelection;
import tn.esprit.skillexchange.Service.GestionFormation.ContentCourseService;
import tn.esprit.skillexchange.Service.GestionFormation.ContentSelectionService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/selectionContent")
public class CourseSelectionRestController {

    @Autowired
    private ContentSelectionService selectionService;


    @GetMapping("/retrieve-all-selection")
    public List<UserCourseContentSelection> getSelections() {
        List<UserCourseContentSelection> listselections = selectionService.retrieveAllselections();
        return listselections;
    }

    @GetMapping("/retrieve-selection/{selection-id}")
    public UserCourseContentSelection retrieveSelection(@PathVariable("selection-id") int selectionId) {
        UserCourseContentSelection selection = selectionService.retrieveSelection(selectionId);
        return selection;
    }
    @GetMapping("/user/{userId}")
    public List<UserCourseContentSelection> getUserSelections(@PathVariable int userId) {
        return selectionService.getUserSelections(userId);

    }
    @GetMapping("/user/{userId}/content/{contentCourseId}")
    public UserCourseContentSelection findByUserIdAndCourseContentId(@PathVariable int userId,@PathVariable int contentCourseId) {
        return selectionService.findByUserIdAndCourseContentId(userId, contentCourseId);

    }

    @PostMapping("/add-selection")
    public UserCourseContentSelection addSelection(@RequestBody UserCourseContentSelection s) {
        UserCourseContentSelection selection = selectionService.addSelection(s);
        return selection;
    }

    @DeleteMapping("/remove-selection/{selection-id}")
    public void removeSelection(@PathVariable("selection-id") int selection) {
        selectionService.removeSeletion(selection);
    }


}
