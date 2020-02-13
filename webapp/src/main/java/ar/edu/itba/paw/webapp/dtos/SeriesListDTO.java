package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.SeriesList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeriesListDTO {

    private Long id;
    private String name;
    private List<SeriesDTO> seriesList;

    public SeriesListDTO() {
        //Empty constructor for JAX-RS
    }

    public SeriesListDTO(SeriesList list) {
        id = list.getId();
        name = list.getName();
        seriesList = new ArrayList<>(list.getSeries().size());
        list.getSeries().stream().forEach(series -> seriesList.add(new SeriesDTO(series)));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SeriesDTO> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<SeriesDTO> seriesList) {
        this.seriesList = seriesList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
