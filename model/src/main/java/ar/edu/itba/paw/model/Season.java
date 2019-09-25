package ar.edu.itba.paw.model;

import java.util.List;

public class Season {

    private String name;
    private List<Episode> episodeList;
    private int seasonNumber;
    private long seasonId;
    private Rating userRating;

    public String getName() {
        return name;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Rating getUserRating() {
        return userRating;
    }

    public void setUserRating() {

    }

    public long getId() {
        return seasonId;
    }

    public void setId(long seasonId) {
        this.seasonId = seasonId;
    }

    public void setEpisodes(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }
}
