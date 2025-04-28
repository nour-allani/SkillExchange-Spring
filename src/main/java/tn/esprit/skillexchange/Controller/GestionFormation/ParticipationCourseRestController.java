package tn.esprit.skillexchange.Controller.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionFormation.ParticipationCourses;
import tn.esprit.skillexchange.Service.GestionFormation.ParticipationCourseService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/participations")
public class ParticipationCourseRestController {
    @Autowired
    ParticipationCourseService participationCourseService;


    @GetMapping("/retrieve-all-participations")
    public List<ParticipationCourses> getParticipations() {
        List<ParticipationCourses> listParticipations = participationCourseService.retrieveAllParticipations();
        return listParticipations;
    }

    @GetMapping("/retrieve-participation/{participation-id}")
    public ParticipationCourses retrieveParticipations(@PathVariable("participation-id") Integer  partId) {
        ParticipationCourses participations = participationCourseService.retrieveParticipation(partId);
        return participations;
    }

    @PostMapping("/add-participation")
    public ParticipationCourses addParticipation(@RequestBody ParticipationCourses p) {
        return participationCourseService.addParticipation(p);
    }

    @DeleteMapping("/remove-participation/{participation-id}")
    public void removeParticipation(@PathVariable("participation-id") Integer  partId) {
        participationCourseService.removeParticipation(partId);
    }



    @PutMapping("/modify-participation")
    public ParticipationCourses modifyParticipation(@RequestBody ParticipationCourses p) {
        ParticipationCourses participation = participationCourseService.modifyParticipation(p);
        return participation;
    }

    @PostMapping("/findById/")
    public List<ParticipationCourses> getParticipationsByIdCourse(@RequestBody  int id) {
        System.out.println("test user"+ id );
        return participationCourseService.getParticipationsByIdCourse(id) ;	}
    @PostMapping("/{participationId}/assign-quiz/{quizId}")
    public void assignQuizToParticipation(
            @PathVariable int participationId,
            @PathVariable Long quizId
    ) {
        participationCourseService.assignQuizToParticipation(participationId, quizId);
    }

    @GetMapping("/check")
    public boolean checkParticipation(
            @RequestParam int participantId,
            @RequestParam int courseId
    ) {
        return participationCourseService.checkParticipation(participantId, courseId);
    }

}
