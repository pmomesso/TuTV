package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Series;

import java.util.*;

public class SeriesDTO {

    private Long id;
    private String name;
    private String network;
    private String seriesDescription;
    private String bannerUrl;
    private Integer loggedInUserRating;
    private boolean loggedInUserFollows;
    private Double userRating;
    private Integer followers;
    private List<SeasonDTO> seasons = Collections.emptyList();

    public SeriesDTO() {
        //Empty constructor for JAX-RS
    }

    public SeriesDTO(Series series) {
        this.id = series.getId();
        this.name = series.getName();
        this.network = series.getNetwork().getName();
        this.seriesDescription = series.getSeriesDescription();
        this.bannerUrl = series.getBannerUrl();
        this.loggedInUserRating = -1;
        this.loggedInUserFollows = false;
        this.userRating = series.getUserRating();
        this.followers = series.getFollowers();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getSeriesDescription() {
        return seriesDescription;
    }

    public void setSeriesDescription(String seriesDescription) {
        this.seriesDescription = seriesDescription;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Integer getLoggedInUserRating() {
        return loggedInUserRating;
    }

    public void setLoggedInUserRating(Integer loggedInUserRating) {
        this.loggedInUserRating = loggedInUserRating;
    }

    public boolean isLoggedInUserFollows() {
        return loggedInUserFollows;
    }

    public void setLoggedInUserFollows(boolean loggedInUserFollows) {
        this.loggedInUserFollows = loggedInUserFollows;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public List<SeasonDTO> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonDTO> seasons) {
        this.seasons = seasons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSeasonsList(Series series) {
        seasons = new ArrayList<>(series.getSeasons().size());
        series.getSeasons().stream().forEach(season -> seasons.add(new SeasonDTO(season)));
        seasons.sort(Comparator.comparingInt(SeasonDTO::getNumber));
    }

}
