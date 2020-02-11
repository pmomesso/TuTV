package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainPageDTO {

    private List<SeriesDTO> banner_series = Collections.emptyList();
    private List<GenreDTO> series_list = Collections.emptyList();

    public MainPageDTO() {
        //Empty constructor for JAX-RS
    }

    public MainPageDTO(List<Series> banner, Map<Genre,List<Series>> seriesByGenre) {
        banner_series = new ArrayList<>(banner_series.size());
        banner.stream().forEach(series -> banner_series.add(new SeriesDTO(series)));
        this.series_list = new ArrayList<>(seriesByGenre.keySet().size());
        seriesByGenre.keySet().stream().forEach(genre -> this.series_list.add(new GenreDTO(genre, seriesByGenre.get(genre))));
    }

    public List<SeriesDTO> getBanner_series() {
        return banner_series;
    }

    public void setBanner_series(List<SeriesDTO> banner_series) {
        this.banner_series = banner_series;
    }

    public List<GenreDTO> getSeries_list() {
        return series_list;
    }

    public void setSeries_list(List<GenreDTO> series_list) {
        this.series_list = series_list;
    }
}
