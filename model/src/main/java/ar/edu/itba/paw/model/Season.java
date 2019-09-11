package ar.edu.itba.paw.model;

import java.util.LinkedList;
import java.util.List;

public class Season {

    private String name;
    private String description;
    private double userRating;
    private int numEpisodes;

    private List<Episode> episodeList = new LinkedList<Episode>();

    public Season(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public void addEpisode(Episode episode) {
        episodeList.add(episode);
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

    public void setNumEpisodes(int numEpisodes) {
        this.numEpisodes = numEpisodes;
    }

}