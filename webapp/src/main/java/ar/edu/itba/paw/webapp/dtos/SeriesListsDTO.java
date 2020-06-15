package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.SeriesList;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeriesListsDTO {

    private List<SeriesListDTO> favourites = Collections.emptyList();

    public SeriesListsDTO() {
        //Empty constructor for JAX-RS
    }

    public SeriesListsDTO(List<SeriesList> list, UriInfo uriInfo) {
        favourites = new ArrayList<>(list.size());
        list.stream().forEach(seriesList -> favourites.add(new SeriesListDTO(seriesList, uriInfo)));
    }

    public List<SeriesListDTO> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<SeriesListDTO> favourites) {
        this.favourites = favourites;
    }
}
