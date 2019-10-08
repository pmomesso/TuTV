package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Episode {

    private String name;
    private String description;
    private int episodeNumber;
    private long id;
    private boolean viewed = false;
    private Rating userRating;
    private Date airing;
    private List<Comment> episodeComments = Collections.emptyList();

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public List<Comment> getEpisodeComments() {
        return episodeComments;
    }

    public void setEpisodeComments(List<Comment> episodeComments) {
        this.episodeComments = episodeComments;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getAiring() {
        return airing;
    }

    public void setAiring(Date airing) {
        this.airing = airing;
    }
}
