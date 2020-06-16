package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Season;
import ar.edu.itba.paw.model.User;

import java.util.*;

public class SeasonDTO {

    private Long id;
    private Integer number;
    private Boolean viewedByUser;
    private List<EpisodeDTO> episodes = Collections.emptyList();

    public SeasonDTO() {
        //Empty constructor for JAX-RS
    }

    public SeasonDTO(Season season, Optional<User> loggedUser) {
        this.id = season.getId();
        this.number = season.getSeasonNumber();
        this.viewedByUser = false;
        setEpisodesList(season, loggedUser);
        setUserFields(season, loggedUser);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isViewedByUser() {
        return viewedByUser;
    }

    public void setViewedByUser(Boolean viewedByUser) {
        this.viewedByUser = viewedByUser;
    }

    public List<EpisodeDTO> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodeDTO> episodes) {
        this.episodes = episodes;
    }

    public void setEpisodesList(Season season, Optional<User> loggedUser) {
        episodes = new ArrayList<>();
        season.getEpisodes().stream().forEach(episode -> {
            EpisodeDTO episodeDTO = new EpisodeDTO(episode);
            episodes.add(episodeDTO);
            episodeDTO.setUserFields(episode, loggedUser);
        });
        episodes.sort(Comparator.comparingInt(EpisodeDTO::getNumEpisode));
    }

    public void setUserFields(Season season, Optional<User> loggedUser) {
        loggedUser.ifPresent(user -> {
            setViewedByUser(season.getViewed());
        });
        if(!loggedUser.isPresent()) {
            viewedByUser = null;
        }
    }
}
