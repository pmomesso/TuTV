package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SeriesService {

    Map<Genre, Set<Series>> getSeriesMapByName(String name);
    List<Series> getSeriesByName(String name);
    List<Series> getSeriesByGenreAndNumber(Genre genre, int num);
    List<Series> getAllSeriesByGenre(String genreName);
    List<Series> getAllSeriesByGenre(Genre genre);
    Map<Genre, List<Series>> getSeriesByGenreMap(int lowerNumber, int upperNumber);
    List<Series> getNewestSeries(int lowerNumber, int upperNumber);

}
