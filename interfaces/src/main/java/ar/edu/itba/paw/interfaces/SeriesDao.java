package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;

import java.util.List;
import java.util.Map;

public interface SeriesDao {

    List<Series> getSeriesByName(String seriesName);

    List<Series> getSeriesByGenre(Genre genre);

    List<Series> getBestSeriesByGenre(Genre genre, int lowerLimit, int upperLimit);

    Map<Genre, List<Series>> getBestSeriesByGenres(int lowerLimit, int upperLimit);

    Series getSeriesById(final long id);

    long createSeries(String seriesName, String seriesDescription);

    void addSeriesGenre(long seriesId, String genre);
    void setSeriesRunningTime(long seriesId, int runningTime);
    void setSeriesNetwork(long seriesId, String network);
    void setSeriesDescription(long seriesId, String seriesDescription);
}
