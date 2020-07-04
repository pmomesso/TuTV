package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenreDTO {

    private Long id;
    private String name;
    private String i18Key;
    private List<SeriesDTO> series = Collections.emptyList();

    private URI seriesUri;

    public GenreDTO() {
        //Empty constructor for JAX-RS
    }

    public GenreDTO(Genre genre, List<Series> seriesList, UriInfo uriInfo) {
        this(genre, uriInfo);
        this.series = new ArrayList<>();
        seriesList.stream().forEach(series -> this.series.add(new SeriesDTO(series, uriInfo)));
    }

    public GenreDTO(Genre genre, UriInfo uriInfo) {
        this(genre);
        seriesUri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).queryParam("page", 1).build();
    }

    public GenreDTO(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
        this.i18Key = genre.getI18Key();
        this.series = null;
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

    public String getI18Key() {
        return i18Key;
    }

    public void setI18Key(String i18Key) {
        this.i18Key = i18Key;
    }

    public URI getSeriesUri() {
        return seriesUri;
    }

    public void setSeriesUri(URI seriesUri) {
        this.seriesUri = seriesUri;
    }
}
