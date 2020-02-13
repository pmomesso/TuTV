package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenresStatsDTO {

    private List<GenreStats> stats;

    public GenresStatsDTO() {
        //Empty constructor for JAX-RS
    }

    public GenresStatsDTO(Map<Genre, Long> genresStats) {
        stats = new ArrayList<>();
        genresStats.keySet().stream().forEach(genre -> {
            stats.add(new GenreStats(genre, genresStats.get(genre)));
        });
    }

    public List<GenreStats> getStats() {
        return stats;
    }

    public void setStats(List<GenreStats> stats) {
        this.stats = stats;
    }
}
