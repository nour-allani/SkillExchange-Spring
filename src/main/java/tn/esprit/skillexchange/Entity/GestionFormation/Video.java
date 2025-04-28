package tn.esprit.skillexchange.Entity.GestionFormation;

public class Video {
    private String title;
    private String channel;
    private String thumbnailUrl;
    private String videoUrl;

    public Video(String title, String channel, String thumbnailUrl, String videoUrl) {
        this.title = title;
        this.channel = channel;
        this.thumbnailUrl = thumbnailUrl;
        this.videoUrl = videoUrl;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
}
