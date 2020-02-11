package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Genre;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GenreListDTO {

    private List<GenreDTO> genres = Collections.emptyList();

    public GenreListDTO() {
        //Empty constructor for JAX-RS
    }

    public GenreListDTO(Collection<Genre> genresCollection) {
        genres = new ArrayList<>();
        genresCollection.stream().forEach(genre -> genres.add(new GenreDTO(genre)));
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }
}
