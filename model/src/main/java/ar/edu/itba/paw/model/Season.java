package ar.edu.itba.paw.model;

import java.util.Collections;
import java.util.List;

public class Season {

    private String name;
    private List<Episode> episodeList = Collections.emptyList();
    private int seasonNumber;
    private long seasonId;
    private boolean viewed;
    private int episodesViewed;
    private boolean seasonAired;
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

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public boolean isViewed() {
        return viewed;
    }

    public int getEpisodesViewed() {
        return episodesViewed;
    }

    public void setEpisodesViewed(int episodesViewed) {
        this.episodesViewed = episodesViewed;
    }

    public boolean isSeasonAired() {
        return seasonAired;
    }

    public void setSeasonAired(boolean seasonAired) {
        this.seasonAired = seasonAired;
    }
}
