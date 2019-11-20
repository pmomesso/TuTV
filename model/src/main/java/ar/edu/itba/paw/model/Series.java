package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "series")
public class Series  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "series_id_seq")
    @SequenceGenerator(sequenceName = "series_id_seq", name = "series_id_seq", allocationSize = 1)
    private Long id = -1L;
    @Column
    private Integer tvdbid;
    @Column(length = 255, nullable = false)
    private String name;
    @Column(name = "description",length = 2048, nullable = true)
    private String seriesDescription;
    @Column(length = 255, nullable = true)
    private String posterUrl;
    @Column(length = 255, nullable = true)
    private String bannerUrl;
    @Column(length = 16, nullable = false)
    private String status;
    @Column
    private Double userRating;
    @Transient
    private Integer loggedInUserRating;
    @Column
    private Integer runtime;
    @Column(columnDefinition = "integer default 0")
    private Integer followers = 0;
    @Column(length = 64)
    private String imdbId;
    @Column
    @Temporal(TemporalType.DATE)
    private Date firstAired;
    @Column
    @Temporal(TemporalType.DATE)
    private Date added;
    @Column
    @Temporal(TemporalType.DATE)
    private Date updated;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY,optional = false)
    private Network network;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinTable(
            name = "hasgenre",
            joinColumns = { @JoinColumn(name = "seriesid") },
            inverseJoinColumns = { @JoinColumn(name = "genreid") }
    )
    private Set<Genre> genres = new HashSet<>();
    @ManyToMany(mappedBy = "series",fetch = FetchType.LAZY)
    private Set<SeriesList> seriesList = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinTable(
            name = "follows",
            joinColumns = { @JoinColumn(name = "seriesid") },
            inverseJoinColumns = { @JoinColumn(name = "userid") }
    )
    private Set<User> userFollowers = new HashSet<>();
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "series",fetch = FetchType.LAZY)
    @OrderBy(value = "seasonNumber asc")
    private Set<Season> seasons = new HashSet<>();
    @OneToMany(mappedBy = "series",fetch = FetchType.LAZY)
    private Set<SeriesReview> seriesReviewList = new HashSet<>();
    @OneToMany(mappedBy = "series",fetch = FetchType.LAZY)
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
    private Set<Notification> notifications = new HashSet<>();

    public Series(String name) {
        this.name = name;
    }

    public Series() {}

    public Series(String name, String seriesDescription) {
        this.name = name;
        this.seriesDescription = seriesDescription;
    }
    public Series(int tvdbid,String name,String seriesDescription,Network network,String posterUrl,String bannerUrl,Double userRating,
                  String status,Integer runtime,int followers,String imdbid,String firstAired,String added,String updated) {
        this.tvdbid = tvdbid;
        this.name = name;
        this.seriesDescription = seriesDescription;
        this.network = network;
        this.posterUrl = posterUrl;
        this.bannerUrl = bannerUrl;
        this.status = status;
        this.userRating = userRating;
        this.runtime = runtime;
        this.followers = followers;
        this.imdbId = imdbid;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.firstAired = f.parse(firstAired);
        } catch (ParseException e) {
            this.firstAired = null;
        }
        try {
            this.added = f.parse(added);
        } catch (ParseException e) {
            this.added = null;
        }
        try {
            this.updated = f.parse(updated);
        } catch (ParseException e) {
            this.updated = null;
        }
    }
    public Series(Long id,int tvdbid,String name,String seriesDescription,Network network,String posterUrl,String bannerUrl,Double userRating,
                  String status,Integer runtime,int followers,String imdbid,String firstAired,String added,String updated) {
        this.id=id;
        this.tvdbid = tvdbid;
        this.name = name;
        this.seriesDescription = seriesDescription;
        this.network = network;
        this.posterUrl = posterUrl;
        this.bannerUrl = bannerUrl;
        this.status = status;
        this.userRating = userRating;
        this.runtime = runtime;
        this.followers = followers;
        this.imdbId = imdbid;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.firstAired = f.parse(firstAired);
        } catch (ParseException e) {
            this.firstAired = null;
        }
        try {
            this.added = f.parse(added);
        } catch (ParseException e) {
            this.added = null;
        }
        try {
            this.updated = f.parse(updated);
        } catch (ParseException e) {
            this.updated = null;
        }
    }
    public int getRuntime() {
        return runtime;
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

    public void addGenre(Genre genre) {
        genres.add(genre);
        genre.getSeries().add(this);
    }

    public void addGenres(Collection<Genre> genres){
        this.genres.addAll(genres);
    }
    public Set<Genre> getGenres(){
        return genres;
    }
    public void setGenres(Set<Genre> genres){
        this.genres = genres;
    }
    public void setRuntime(int runningTime) {
        this.runtime = runningTime;
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

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
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
        return id.equals(((Series)obj).id);
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

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }

    public void addSeason(Season season){
        this.seasons.add(season);
        season.setSeries(this);
    }
    public void setSeriesPostList(Set<SeriesReview> seriesReviewList) {
        this.seriesReviewList = seriesReviewList;
    }

    public Set<SeriesReview> getSeriesReviewList() {
        return seriesReviewList;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<User> getUserFollowers() {
        return userFollowers;
    }

    public void setUserFollowers(Set<User> userFollowers) {
        this.userFollowers = userFollowers;
    }

    public void addUserFollower(User user){
        this.userFollowers.add(user);
        user.getFollows().add(this);
    }
    public void removeUserFollower(User user){
        this.userFollowers.remove(user);
        user.getFollows().remove(this);
    }
    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Integer getLoggedInUserRating() {
        return loggedInUserRating;
    }

    public void setLoggedInUserRating(Integer loggedInUserRating) {
        this.loggedInUserRating = loggedInUserRating;
    }

    public Set<SeriesList> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(Set<SeriesList> seriesList) {
        this.seriesList = seriesList;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
}
