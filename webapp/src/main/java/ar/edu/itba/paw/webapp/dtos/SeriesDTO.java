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

    private URI reviews;

    public SeriesDTO() {
        //Empty constructor for JAX-RS
    }

    public SeriesDTO(Series series, Optional<User> loggedUser, UriInfo uriInfo) {
        this(series);
        setUserFields(series, loggedUser);
        setReviewsUri(uriInfo);
    }

    private void setReviewsUri(UriInfo uriInfo) {
        reviews = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(id))
                .path("reviews").build();
    }

    public SeriesDTO(Series series) {
        this.id = series.getId();
        this.name = series.getName();
        this.network = series.getNetwork().getName();
        this.seriesDescription = series.getSeriesDescription();
        this.bannerUrl = series.getBannerUrl();
        this.posterUrl = series.getPosterUrl();
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

    public void setSeasonsList(Series series, Optional<User> loggedUser) {
        seasons = new ArrayList<>();
        series.getSeasons().stream().forEach(season -> {
            SeasonDTO seasonDTO = new SeasonDTO(season);
            seasonDTO.setEpisodesList(season, loggedUser);
            seasonDTO.setUserFields(season, loggedUser);
            seasons.add(seasonDTO);
        });
        seasons.sort(Comparator.comparingInt(SeasonDTO::getNumber));
    }

    public void setReviews(URI reviews) {
        this.reviews = reviews;
    }

    public Boolean getLoggedInUserFollows() {
        return loggedInUserFollows;
    }

    public URI getReviews() {
        return reviews;
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
