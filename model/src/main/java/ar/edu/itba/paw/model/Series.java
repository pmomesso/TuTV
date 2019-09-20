package ar.edu.itba.paw.model;

import java.util.*;

public class Series  {

    private String seriesName;
    private String seriesDescription;
    private String network;
    private String rating;
    private Rating userRating;
    private int runningTime;
    private int status;
    private long imbdId;
    private Date firstAired;
    private Date added;
    private Date updated;
    private List<ActorRole> actorList = new LinkedList<ActorRole>();
    private Set<Genre> genresList = new HashSet<>();

    public Series(String seriesName) {
        this.seriesName = seriesName;
    }

    public Series() {}

    public Series(String seriesName, String seriesDescription) {
        this.seriesName = seriesName;
        this.seriesDescription = seriesDescription;
    }

    public String getNetwork() {
        return network;
    }

    public String getRating() {
        return rating;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public int getStatus() {
        return status;
    }

    public long getImbdId() {
        return imbdId;
    }

    public Date getFirstAired() {
        return firstAired;
    }

    public Date getAdded() {
        return added;
    }

    public Date getUpdated() {
        return updated;
    }

    public Rating getUserRating() {
        return userRating;
    }

    public void setUserRating(Rating rating) { userRating = rating; }

    public String getSeriesName() {
        return seriesName;
    }

    public String getSeriesDescription() {
        return seriesDescription;
    }

    public void setDescription(String seriesDescription) {
        this.seriesDescription = seriesDescription;
    }

    public void addActor(ActorRole actorRole) {
        actorList.add(actorRole);
    }

    public void addGenre(Genre genre) {
        genresList.add(genre);
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

}
