package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenreDTO {

    private String name;
    private List<SeriesDTO> series = Collections.emptyList();

    public GenreDTO() {
        //Empty constructor for JAX-RS
    }

    public GenreDTO(Genre genre, List<Series> seriesList) {
        this.name = genre.getName();
        this.series = new ArrayList<>(seriesList.size());
        seriesList.stream().forEach(series -> this.series.add(new SeriesDTO(series)));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SeriesDTO> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesDTO> series) {
        this.series = series;
    }
}
