package ar.edu.itba.paw.webapp.dtos;

public class ToBeSeenEpisodeDTO {

    private SeriesDTO series;
    private EpisodeDTO episode;

    public ToBeSeenEpisodeDTO() {}

    public ToBeSeenEpisodeDTO(SeriesDTO series, EpisodeDTO episode) {
        this.series = series;
        this.episode = episode;
    }

    public SeriesDTO getSeries() {
        return series;
    }

    public void setSeries(SeriesDTO series) {
        this.series = series;
    }

    public EpisodeDTO getEpisode() {
        return episode;
    }

    public void setEpisode(EpisodeDTO episode) {
        this.episode = episode;
    }
}
