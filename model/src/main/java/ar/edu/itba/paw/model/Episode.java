package ar.edu.itba.paw.model;

public class Episode {

    private String name;
    private String description;
    private int episodeNumber;
    private Rating userRating;

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

    public Rating getUserRating() {
        return userRating;
    }

    public void setUserRating(Rating rating) { userRating = rating; }

}
