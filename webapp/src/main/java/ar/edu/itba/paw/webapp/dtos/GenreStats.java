package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Genre;

public class GenreStats {

    private GenreDTO genre;
    private Long stat;

    public GenreStats() {
        //Empty constructor for JAX-RS
    }

    public GenreStats(Genre genre, Long stat) {
        this.genre = new GenreDTO(genre);
        this.stat = stat;
    }

    public GenreDTO getGenre() {
        return genre;
    }

    public void setGenre(GenreDTO genre) {
        this.genre = genre;
    }

    public Long getStat() {
        return stat;
    }

    public void setStat(Long stat) {
        this.stat = stat;
    }
}
