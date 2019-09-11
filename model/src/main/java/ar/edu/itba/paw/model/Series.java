package ar.edu.itba.paw.model;


import java.util.LinkedList;
import java.util.List;

public class Series  {

    private String seriesName;
    private String seriesDescription;
    private String network;
    private double userRating;
    private int runningTime;
    private List<String> genresList = new LinkedList<String>();
    private List<Season> seasonList = new LinkedList<Season>();
    private List<Actor> actorList = new LinkedList<Actor>();

    public Series(String seriesName) {
        this.seriesName = seriesName;
    }

    public Series(String seriesName, String seriesDescription) {
        this.seriesName = seriesName;
        this.seriesDescription = seriesDescription;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public String getSeriesDescription() {
        return seriesDescription;
    }

    public void setDescription(String seriesDescription) {
        this.seriesDescription = seriesDescription;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public void addActor(Actor actor) {
        actorList.add(actor);
    }

    public void addSeason(Season season) {
        seasonList.add(season);
    }

    public void addGenre(String genre) {
        genresList.add(genre);
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

}
