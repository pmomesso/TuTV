package ar.edu.itba.paw.model;

public class Episode {

    private String name;
    private String description;
    private int episodeNumber;
    private double userRating;

    public Episode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

}
