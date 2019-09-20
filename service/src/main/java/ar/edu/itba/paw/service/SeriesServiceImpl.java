package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesServiceImpl implements SeriesService {

    @Autowired
    private SeriesDao seriesDao;

    @Autowired
    public SeriesServiceImpl(SeriesDao seriesDao) {
        this.seriesDao = seriesDao;
    }

    @Override
    public List<Series> getSeriesByGenreAndNumber(Genre genre, int num) {
        return seriesDao.getBestSeriesByGenre(genre, num);
    }

    @Override
    public List<Series> getAllSeriesByGenre(Genre genre) {
        return seriesDao.getSeriesByGenre(genre);
    }
}
