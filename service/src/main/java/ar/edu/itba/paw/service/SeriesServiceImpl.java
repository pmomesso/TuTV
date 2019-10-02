package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Season;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeriesServiceImpl implements SeriesService {

    @Autowired
    private SeriesDao seriesDao;
    @Autowired
    private UserService userService;

    @Autowired
    public SeriesServiceImpl(SeriesDao seriesDao) {
        this.seriesDao = seriesDao;
    }

    @Override
    public List<Series> searchSeries(String seriesName, String genreName, String networkName,int minRating,int maxRating) {
        String name = seriesName == null ? "" : seriesName;
        String genre = genreName == null ? "" : genreName;
        String network = networkName == null ? "" : networkName;
        return seriesDao.searchSeries(name,genre,network,minRating,maxRating);
    }

    @Override
    public List<Series> getSeriesByName(String name){
        return seriesDao.getSeriesByName(name);
    }

    @Override
    public Series getSerieById(long id) {
        return seriesDao.getSeriesById(id, userService.getLoggedUser().getId());
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
    public void followSeries(long seriesId) {
        seriesDao.followSeries(seriesId, userService.getLoggedUser().getId());
    }

    @Override
    public void setViewedEpisode(long episodeId) {
        seriesDao.setViewedEpisode(episodeId, userService.getLoggedUser().getId());
    }

    @Override
    public void rateSeries(long seriesId, double rating) {
        seriesDao.rateSeries(seriesId,userService.getLoggedUser().getId(),rating);
    }

    @Override
    public void unviewEpisode(long episodeId) {
        seriesDao.unviewEpisode(userService.getLoggedUser().getId(), episodeId);
    }

    @Override
    public void addSeriesReview(String body, long seriesId) {
        seriesDao.addSeriesReview(body, seriesId, userService.getLoggedUser().getId());
    }

    @Override
    public void likePost(long postId) {
        seriesDao.likePost(userService.getLoggedUser().getId(), postId);
    }

    @Override
    public void unlikePost(long postId) {
        seriesDao.unlikePost(userService.getLoggedUser().getId(), postId);
    }

    @Override
    public void addCommentToPost(long commentPostId, String commentBody) {
        seriesDao.addCommentToPost(commentPostId, commentBody, userService.getLoggedUser().getId());
    }

    @Override
    public void likeComment(long commentId) {
        seriesDao.likeComment(userService.getLoggedUser().getId(), commentId);
    }

    @Override
    public void unlikeComment(long commentId) {
        seriesDao.unlikeComment(userService.getLoggedUser().getId(), commentId);
    }

    @Override
    public void removeComment(long commentId) throws BadRequestException {
        if(!userService.getLoggedUser().getIsAdmin()) throw new BadRequestException();
        seriesDao.removeComment(commentId);
    }

    @Override
    public void removePost(long postId) throws BadRequestException {
        if(!userService.getLoggedUser().getIsAdmin()) throw new BadRequestException();
        seriesDao.removePost(postId);
    }
}
