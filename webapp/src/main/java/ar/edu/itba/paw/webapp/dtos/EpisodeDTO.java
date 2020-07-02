package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Episode;
import ar.edu.itba.paw.model.User;

import java.util.Optional;

public class EpisodeDTO {

    private Long id;
    private String name;
    private String air_date;
    private Boolean viewedByUser;
    private Integer numEpisode;
    private Integer seasonNumber;

    public EpisodeDTO() {
        //Empty constructor for JAX-RS
    }

    public EpisodeDTO(Episode episode) {
        this.id = episode.getId();
        this.name = episode.getName();
        if(episode.getAiring() != null) {
            this.air_date = episode.getAiring().toString();
        }
        this.numEpisode = episode.getNumEpisode();
        this.seasonNumber = episode.getSeason().getSeasonNumber();
    }
    public EpisodeDTO(Episode episode, Optional<User> loggedInUser) {
        this.id = episode.getId();
        this.name = episode.getName();
        if(episode.getAiring() != null) {
            this.air_date = episode.getAiring().toString();
        }
        this.numEpisode = episode.getNumEpisode();
        this.seasonNumber = episode.getSeason().getSeasonNumber();
        this.setUserFields(episode,loggedInUser);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public Boolean isViewedByUser() {
        return viewedByUser;
    }

    public void setViewedByUser(Boolean viewedByUser) {
        this.viewedByUser = viewedByUser;
    }

    public Integer getNumEpisode() {
        return numEpisode;
    }

    public void setNumEpisode(Integer numEpisode) {
        this.numEpisode = numEpisode;
    }

    public void setUserFields(Episode episode, Optional<User> loggedUser) {
        loggedUser.ifPresent(user -> {
            setViewedByUser(episode.getViewers().contains(user));
        });
        if(!loggedUser.isPresent()) {
            viewedByUser = null;
        }
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
}
