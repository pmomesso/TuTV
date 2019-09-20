package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;

import java.util.List;
import java.util.Map;

public interface SeriesService {

    List<Series> getSeriesByGenreAndNumber(Genre genre, int num);
    List<Series> getAllSeriesByGenre(Genre genre);
    Map<Genre, List<Series>> getSeriesByGenreMap(int lowerNumber, int upperNumber);

}
