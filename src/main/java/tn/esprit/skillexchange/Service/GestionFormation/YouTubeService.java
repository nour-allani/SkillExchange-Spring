package tn.esprit.skillexchange.Service.GestionFormation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tn.esprit.skillexchange.Entity.GestionFormation.Video;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class YouTubeService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${youtube.api.key}")
    private String apiKey;

    private static final String API_URL = "https://www.googleapis.com/youtube/v3/search";
    private Map<String, List<Video>> cache = new HashMap<>();

    public List<Video> getVideos(String query) throws Exception {
        if (cache.containsKey(query)) {
            return cache.get(query);
        }

        String url = API_URL + "?part=snippet&q=" + URLEncoder.encode(query, StandardCharsets.UTF_8) +
                "&type=video&maxResults=3&videoCategoryId=27&key=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        JsonNode items = jsonNode.get("items");
        List<Video> videos = new ArrayList<>();

        if (items != null) {
            for (JsonNode item : items) {
                JsonNode snippet = item.get("snippet");
                String title = snippet.has("title") ? snippet.get("title").asText() : "Unknown Title";
                String channel = snippet.has("channelTitle") ? snippet.get("channelTitle").asText() : "Unknown Channel";
                String thumbnailUrl = snippet.has("thumbnails") && snippet.get("thumbnails").has("default")
                        ? snippet.get("thumbnails").get("default").get("url").asText()
                        : null;
                String videoId = item.has("id") && item.get("id").has("videoId") ? item.get("id").get("videoId").asText() : null;
                String videoUrl = videoId != null ? "https://www.youtube.com/watch?v=" + videoId : null;

                videos.add(new Video(title, channel, thumbnailUrl, videoUrl));
            }
        }

        cache.put(query, videos);
        return videos;
    }
}
