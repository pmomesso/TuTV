package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Season;

import java.util.*;

public class SeasonDTO {

    private Long id;
    private Integer number;
    private boolean viewedByUser;
    private List<EpisodeDTO> episodes = Collections.emptyList();

    public SeasonDTO() {
        //Empty constructor for JAX-RS
    }

    public SeasonDTO(Season season) {
        this.id = season.getId();
        this.number = season.getSeasonNumber();
        this.viewedByUser = false;
        setEpisodesList(season);
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

    public boolean isViewedByUser() {
        return viewedByUser;
    }

    public void setViewedByUser(boolean viewedByUser) {
        this.viewedByUser = viewedByUser;
    }

    public List<EpisodeDTO> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodeDTO> episodes) {
        this.episodes = episodes;
    }

    public void setEpisodesList(Season season) {
        //episodes = new ArrayList<>(season.getEpisodes().size());
        episodes = new ArrayList<>();
        season.getEpisodes().stream().forEach(episode -> episodes.add(new EpisodeDTO(episode)));
        episodes.sort(Comparator.comparingInt(EpisodeDTO::getNumEpisode));
    }

}
