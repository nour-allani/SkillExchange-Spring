package tn.esprit.skillexchange.Service.GestionFormation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class VideoSummarizerService {

//    public String summarizeVideoCourse(String videoUrl) {
//        try {
//            System.out.println("🔵 Début traitement pour : " + videoUrl);
//
//            String audioPath = downloadAudioFromYoutube(videoUrl);
//            System.out.println("🟢 Audio téléchargé à : " + audioPath);
//
//            String transcription = transcribeAudio(audioPath);
//            System.out.println("🟢 Transcription OK : " + transcription.substring(0, Math.min(200, transcription.length())) + "...");
//
//            String summary = summarizeText(transcription);
//            System.out.println("🟢 Résumé généré : " + summary);
//
//            return summary;
//        } catch (Exception e) {
//            System.out.println("🔴 ERREUR pendant traitement : " + e.getMessage());
//            e.printStackTrace();
//            return "Erreur pendant le traitement.";
//        }
//    }
//    private String downloadAudioFromYoutube(String videoUrl) throws IOException, InterruptedException {
//        String fileName = "audio.mp3";
//        ProcessBuilder pb = new ProcessBuilder("C:\\ytdlp\\yt-dlp.exe", "-x", "--audio-format", "mp3", "-o", fileName, videoUrl);
//        Process process = pb.start();
//        process.waitFor();
//        return fileName;
//    }
//
//    private String transcribeAudio(String audioPath) throws IOException, InterruptedException {
//        // Appel API Whisper
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://api-inference.huggingface.co/models/openai/whisper-small"))
//                .header("Authorization", "Bearer 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")
//                .POST(HttpRequest.BodyPublishers.ofFile(Paths.get(audioPath)))
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body(); // Normalement tu dois parser pour obtenir juste le texte
//    }
//
//    private String summarizeText(String text) throws IOException, InterruptedException {
//        // Appel API Résumé (BART ou Pegasus)
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://api-inference.huggingface.co/models/facebook/bart-large-cnn"))
//                .header("Authorization", "Bearer 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")
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString("{\"inputs\":\"" + text + "\"}"))
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body(); // Tu dois parser pour obtenir seulement le résumé
//    }

    public String extractSubtitles(String url) throws IOException {
        // Déterminer le chemin du dossier "Téléchargements" sur l'ordinateur de l'utilisateur
        String userHome = System.getProperty("user.home");
        File downloadsDir = new File(userHome, "Downloads");

        if (!downloadsDir.exists()) {
            throw new IOException("Le dossier Téléchargements n'existe pas.");
        }

        // Définir le chemin complet pour les sous-titres
        String subtitlesPath = downloadsDir.getAbsolutePath() + File.separator + "video_subtitles.vtt";

        // Commande yt-dlp pour extraire les sous-titres sans télécharger la vidéo
        ProcessBuilder processBuilder = new ProcessBuilder("yt-dlp", "--write-sub", "--sub-lang", "en", "--skip-download", "--output", subtitlesPath, url);
        processBuilder.inheritIO();

        Process process = processBuilder.start();

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Erreur lors de l'extraction des sous-titres.");
            }
        } catch (InterruptedException e) {
            throw new IOException("Le processus a été interrompu.", e);
        }

        // Retourner le chemin du fichier sous-titres dans le dossier Téléchargements
        return subtitlesPath;
    }
}
