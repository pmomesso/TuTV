package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Series;

import java.util.List;

public interface SeriesDao {

    List<Series> getSeriesByName(String seriesName);
    Series getSeriesById(final long id);
    long createSeries(String seriesName, String seriesDescription);

    void addSeriesGenre(long seriesId, String genre);

    void setSeriesRunningTime(long seriesId, int runningTime);
    void setSeriesNetwork(long seriesId, String network);
    void setSeriesDescription(long seriesId, String seriesDescription);
}
