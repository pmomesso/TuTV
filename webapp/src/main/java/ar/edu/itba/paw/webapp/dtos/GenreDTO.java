package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenreDTO {

    private Long id;
    private String name;
    private List<SeriesDTO> series = Collections.emptyList();
    private Long page;
    private boolean arePrevious;
    private boolean areNext;

    public GenreDTO() {
        //Empty constructor for JAX-RS
    }

    public GenreDTO(Genre genre, List<Series> seriesList) {
        this(genre);
        this.series = new ArrayList<>();
        seriesList.stream().forEach(series -> this.series.add(new SeriesDTO(series)));
        this.page = genre.getPage();
        this.arePrevious = genre.isArePrevious();
        this.areNext = genre.isAreNext();
    }

    public GenreDTO(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public boolean isArePrevious() {
        return arePrevious;
    }

    public void setArePrevious(boolean arePrevious) {
        this.arePrevious = arePrevious;
    }

    public boolean isAreNext() {
        return areNext;
    }

    public void setAreNext(boolean areNext) {
        this.areNext = areNext;
    }
}
