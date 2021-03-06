package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Rating;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.*;

public class SeriesDTO {

    private Long id;
    private String name;
    private String network;
    private String seriesDescription;
    private String bannerUrl;
    private String posterUrl;
    private Integer loggedInUserRating = null;
    private Boolean loggedInUserFollows = null;
    private Double userRating;
    private Integer followers;
    private List<SeasonDTO> seasons = null;

    private URI seasonsUri;
    private URI reviewsUri;

    public SeriesDTO() {
        //Empty constructor for JAX-RS
    }
    public SeriesDTO(Series series, Optional<User> loggedInUser, UriInfo uriInfo) {
        this.id = series.getId();
        this.name = series.getName();
        this.network = series.getNetwork().getName();
        this.seriesDescription = series.getSeriesDescription();
        this.bannerUrl = series.getBannerUrl();
        this.posterUrl = series.getPosterUrl();
        this.userRating = series.getUserRating();
        this.followers = series.getFollowers();
        this.setReviewsUri(uriInfo);
        setUserFields(series, loggedInUser);
    }

    public void setSeasonFields(Series series, Optional<User> loggedInUser) {
        this.seasons = new ArrayList<>();
        series.getSeasons().forEach(s -> seasons.add(new SeasonDTO(s,loggedInUser)));
    }

    public SeriesDTO(Series series, UriInfo uriInfo) {
        this.id = series.getId();
        this.name = series.getName();
        this.network = series.getNetwork().getName();
        this.seriesDescription = series.getSeriesDescription();
        this.bannerUrl = series.getBannerUrl();
        this.posterUrl = series.getPosterUrl();
        this.userRating = series.getUserRating();
        this.followers = series.getFollowers();
        setUris(uriInfo);
    }


    private void setUris(UriInfo uriInfo) {
        setReviewsUri(uriInfo);
        setSeasonsUri(uriInfo);
    }

    private void setSeasonsUri(UriInfo uriInfo) {
        seasonsUri = uriInfo.getBaseUriBuilder()
                .path("series")
                .path(String.valueOf(id))
                .path("seasons")
                .build();
    }

    private void setReviewsUri(UriInfo uriInfo) {
        reviewsUri = uriInfo.getBaseUriBuilder()
                .path("series")
                .path(String.valueOf(id))
                .path("reviews").build();
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

    public Boolean isLoggedInUserFollows() {
        return loggedInUserFollows;
    }

    public void setLoggedInUserFollows(Boolean loggedInUserFollows) {
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

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public URI getSeasonsUri() {
        return seasonsUri;
    }

    public void setSeasonsUri(URI seasonsUri) {
        this.seasonsUri = seasonsUri;
    }

    public void setReviewsUri(URI reviewsUri) {
        this.reviewsUri = reviewsUri;
    }

    public Boolean getLoggedInUserFollows() {
        return loggedInUserFollows;
    }

    public URI getReviewsUri() {
        return reviewsUri;
    }

    private void setUserFields(Series series, Optional<User> loggedUser) {
        if(loggedUser.isPresent()) {
            setLoggedInUserFollows(series.getUserFollowers().contains(loggedUser.get()));
            for (Rating rating : loggedUser.get().getRatings()) {
                if (rating.getSeries().getId() == series.getId()) {
                    setLoggedInUserRating(rating.getRating());
                    break;
                }
            }
        }
    }
}
