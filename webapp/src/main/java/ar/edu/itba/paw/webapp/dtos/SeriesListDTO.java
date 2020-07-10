package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.SeriesList;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeriesListDTO {

    private Long id;
    private String name;
    private URI seriesUri;

    public SeriesListDTO() {
        //Empty constructor for JAX-RS
    }

    public SeriesListDTO(SeriesList list, UriInfo uriInfo) {
        id = list.getId();
        name = list.getName();
        seriesUri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(list.getId())).path("series").build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getSeriesUri() {
        return seriesUri;
    }

    public void setSeriesUri(URI seriesUri) {
        this.seriesUri = seriesUri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
