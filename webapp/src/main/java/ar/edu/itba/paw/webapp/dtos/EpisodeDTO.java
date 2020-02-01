package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Episode;

public class EpisodeDTO {

    private Long id;
    private String name;
    private String air_date;
    private boolean viewedByUser;
    private Integer numEpisode;

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

    public boolean isViewedByUser() {
        return viewedByUser;
    }

    public void setViewedByUser(boolean viewedByUser) {
        this.viewedByUser = viewedByUser;
    }

    public Integer getNumEpisode() {
        return numEpisode;
    }

    public void setNumEpisode(Integer numEpisode) {
        this.numEpisode = numEpisode;
    }
}
