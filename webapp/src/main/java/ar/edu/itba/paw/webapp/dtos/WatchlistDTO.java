package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Episode;
import ar.edu.itba.paw.model.Series;

import javax.ws.rs.core.UriInfo;


public class WatchlistDTO {

    private EpisodeDTO episode;
    private SeriesDTO series;

    public WatchlistDTO(){
    }

    public WatchlistDTO(Episode episode, UriInfo uriInfo){
        this.episode = new EpisodeDTO(episode);
        this.series = new SeriesDTO(episode.getSeason().getSeries(), uriInfo);
    }

    public EpisodeDTO getEpisode() {
        return episode;
    }

    public void setEpisode(EpisodeDTO episode) {
        this.episode = episode;
    }

    public SeriesDTO getSeries() {
        return series;
    }

    public void setSeries(SeriesDTO series) {
        this.series = series;
    }
}
