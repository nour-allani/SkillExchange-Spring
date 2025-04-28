package tn.esprit.skillexchange.Controller.GestionFormation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionFormation.Video;
import tn.esprit.skillexchange.Service.GestionFormation.YouTubeService;

import java.util.List;
@RestController
@RequestMapping("/api/youtube")
public class YouTubeController {
    @Autowired
    private YouTubeService youTubeService;

    @GetMapping("/videos")
    public ResponseEntity<List<Video>> getVideos(@RequestParam String query) {
        try {
            List<Video> videos = youTubeService.getVideos(query);
            return ResponseEntity.ok(videos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
