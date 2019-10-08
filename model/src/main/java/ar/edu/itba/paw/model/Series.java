package ar.edu.itba.paw.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Series  {

    private boolean follows;
    private long id;
    private int tvdbid;
    private String name;
    private String seriesDescription;
    private String network;
    private String posterUrl;
    private String bannerUrl;
    private String status;
    private Double userRating;
    private Double totalRating;
    private Integer runningTime;
    private int numFollowers;
    private String imdbId;
    private Date firstAired;
    private Date added;
    private Date updated;
    private Set<Genre> genresSet = new HashSet<>();
    private List<ActorRole> actorList = Collections.emptyList();
    private List<Season> seasons = Collections.emptyList();
    private List<Post> postList = Collections.emptyList();

    public Series(String name) {
        this.name = name;
    }

    public Series() {}

    public Series(String name, String seriesDescription) {
        this.name = name;
        this.seriesDescription = seriesDescription;
    }
    public Series(long id,int tvdbid,String name,String seriesDescription,String network,String posterUrl,String bannerUrl,Double userRating,
                  String status,Integer runningTime,int numFollowers,String imdbid,String firstAired,String added,String updated) {
        this.id = id;
        this.tvdbid = tvdbid;
        this.name = name;
        this.seriesDescription = seriesDescription;
        this.network = network;
        this.posterUrl = posterUrl;
        this.bannerUrl = bannerUrl;
        this.status = status;
        this.userRating = userRating;
        this.runningTime = runningTime;
        this.numFollowers = numFollowers;
        this.imdbId = imdbid;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.firstAired = f.parse(firstAired);
        } catch (ParseException e) {
            this.firstAired = null;
            e.printStackTrace();
        }
        try {
            this.added = f.parse(added);
        } catch (ParseException e) {
            this.added = null;
            e.printStackTrace();
        }
        try {
            this.updated = f.parse(updated);
        } catch (ParseException e) {
            this.updated = null;
            e.printStackTrace();
        }
    }

    public int getRunningTime() {
        return runningTime;
    }

    public String getStatus() {
        return status;
    }

    public String getImdbId() {
        return imdbId;
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

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) { this.userRating = userRating; }

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
        genresSet.add(genre);
    }

    public void addGenres(Collection<Genre> genres){
        genresSet.addAll(genres);
    }
    public Set<Genre> getGenres(){
        return genresSet;
    }
    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public void setFirstAired(Date firstAired) {
        this.firstAired = firstAired;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public int getTvdbid() {
        return tvdbid;
    }

    public void setTvdbid(int tvdbid) {
        this.tvdbid = tvdbid;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public void setSeriesPostList(List<Post> postList) {
        this.postList = postList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getFollows() {
        return follows;
    }

    public void setFollows(boolean follows) {
        this.follows = follows;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }
}
