package tn.esprit.skillexchange.Controller.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ParticipationCourses retrieveParticipations(@PathVariable("participation-id") Long partId) {
        ParticipationCourses participations = participationCourseService.retrieveParticipation(partId);
        return participations;
    }

    @PostMapping("/add-participation")
    public ParticipationCourses addParticipation(@RequestBody ParticipationCourses g) {
        ParticipationCourses participation = participationCourseService.addParticipation(g);
        return participation;
    }

    @DeleteMapping("/remove-participation/{participation-id}")
    public void removeParticipation(@PathVariable("participation-id") Long partId) {
        participationCourseService.removeParticipation(partId);
    }



    @PutMapping("/modify-participation")
    public ParticipationCourses modifyParticipation(@RequestBody ParticipationCourses p) {
        ParticipationCourses participation = participationCourseService.modifyParticipation(p);
        return participation;
    }

    @PostMapping("findById/")
    public List<ParticipationCourses> getParticipationsByIdCourse(@RequestBody  int id) {
        System.out.println("test user"+ id );
        return participationCourseService.getParticipationsByIdCourse(id) ;	}



}
