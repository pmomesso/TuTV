package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Season;
import ar.edu.itba.paw.model.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeriesServiceImpl implements SeriesService {

    @Autowired
    private SeriesDao seriesDao;

    @Autowired
    public SeriesServiceImpl(SeriesDao seriesDao) {
        this.seriesDao = seriesDao;
    }

    @Override
    public List<Series> searchSeries(String seriesName, String genreName, String networkName) {
        String name = seriesName == null ? "" : seriesName;
        String genre = genreName == null ? "" : genreName;
        String network = networkName == null ? "" : networkName;
        return seriesDao.searchSeries(name,genre,network);
    }

    @Override
    public List<Series> getSeriesByName(String name){
        return seriesDao.getSeriesByName(name);
    }

    @Override
    public Series getSerieById(long id, long userId) {
        return seriesDao.getSeriesById(id, userId);
    }

    @Override
    public List<Series> getSeriesByGenreAndNumber(int genreId, int num) {
        return seriesDao.getBestSeriesByGenre(genreId, 0, num);
    }

    @Override
    public List<Series> getAllSeriesByGenre(String genreName) {
        return seriesDao.getSeriesByGenre(genreName);
    }
    @Override
    public List<Series> getAllSeriesByGenre(int id) {
        return seriesDao.getSeriesByGenre(id);
    }

    @Override
    public Map<Genre, List<Series>> getSeriesByGenreMap(int lowerNumber, int upperNumber) {
        return seriesDao.getBestSeriesByGenres(lowerNumber, upperNumber);
    }

    @Override
    public List<Series> getNewestSeries(int lowerNumber, int upperNumber) {
        return seriesDao.getNewSeries(lowerNumber, upperNumber);
    }

    @Override
    public List<Season> getSeasonsBySeriesId(long seriesId) {
        return seriesDao.getSeasonsBySeriesId(seriesId);
    }

    @Override
    public List<Genre> getAllGenres() {
        return seriesDao.getAllGenres();
    }

    @Override
    public void followSeries(long seriesId, long userId) {
        seriesDao.followSeries(seriesId, userId);
    }

    @Override
    public void setViewedEpisode(long episodeId, long userId) {
        seriesDao.setViewedEpisode(episodeId,userId);
    }
}
