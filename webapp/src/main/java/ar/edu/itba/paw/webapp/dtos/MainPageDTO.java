package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Series;

import java.util.*;

public class MainPageDTO {

    private List<SeriesDTO> banner_series = Collections.emptyList();

    public MainPageDTO() {
        //Empty constructor for JAX-RS
    }

    public MainPageDTO(List<Series> banner) {
        banner_series = new ArrayList<>();
        banner.stream().forEach(series -> banner_series.add(new SeriesDTO(series)));
    }

    public List<SeriesDTO> getBanner_series() {
        return banner_series;
    }

    public void setBanner_series(List<SeriesDTO> banner_series) {
        this.banner_series = banner_series;
    }

}
