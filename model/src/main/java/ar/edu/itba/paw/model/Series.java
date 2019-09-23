package ar.edu.itba.paw.model;

import java.util.*;

public class Series  {

    private String name;
    private String seriesDescription;
    private String network;
    private String rating;
    private String posterUrl;
    private String bannerUrl;
    private String status;
    private Rating userRating;
    private int runningTime;
    private int numFollowers;
    private long imbdId;
    private long id;
    private Date firstAired;
    private Date added;
    private Date updated;
    private List<ActorRole> actorList = new LinkedList<ActorRole>();
    private Set<Genre> genresList = new HashSet<>();

    public Series(String name) {
        this.name = name;
    }

    public Series() {}

    public Series(String name, String seriesDescription) {
        this.name = name;
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

    public String getStatus() {
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

    public String getName() {
        return name;
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

    public void addGenres(Collection<Genre> genres){
        genresList.addAll(genres);
    }
    public Set<Genre> getGenres(){
        return genresList;
    }
    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(!(obj instanceof Series))
            return false;
        return id == ((Series)obj).id;
    }
    @Override
    public int hashCode(){
        return Long.valueOf(id).hashCode();
    }

}
