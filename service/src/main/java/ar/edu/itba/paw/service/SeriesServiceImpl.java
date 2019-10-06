package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Season;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
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
    public Series getSerieById(long id) throws NotFoundException {
        User u = userService.getLoggedUser();
        long userId = (u == null) ? -1 : u.getId();
        Series series = seriesDao.getSeriesById(id, userId).orElseThrow(NotFoundException::new);
        return series;
    }

    @Override
    public List<Series> getSeriesByGenreAndNumber(int genreId, int num) throws NotFoundException {
        List<Series> seriesList = seriesDao.getBestSeriesByGenre(genreId, 0, num);
        if(seriesList.isEmpty()) {
            throw new NotFoundException();
        }
        return seriesDao.getBestSeriesByGenre(genreId, 0, num);
    }

    @Override
    public List<Series> getAllSeriesByGenre(String genreName) {
        return seriesDao.getSeriesByGenre(genreName);
    }

    @Override
    public List<Series> getAllSeriesByGenre(int id) throws NotFoundException {
        List<Series> seriesList = seriesDao.getSeriesByGenre(id);
        if(seriesList.isEmpty()) {
            throw new NotFoundException();
        }
        return seriesList;
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
    public List<Season> getSeasonsBySeriesId(long seriesId) throws NotFoundException {
        List<Season> seriesList = seriesDao.getSeasonsBySeriesId(seriesId);
        if(seriesList.isEmpty()) {
            throw new NotFoundException();
        }
        return seriesDao.getSeasonsBySeriesId(seriesId);
    }

    @Override
    public List<Genre> getAllGenres() {
        return seriesDao.getAllGenres();
    }

    @Override
    public void followSeries(long seriesId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.followSeries(seriesId, user.getId());
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void setViewedEpisode(long episodeId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.setViewedEpisode(episodeId, user.getId());
        if(result == 0) {
            throw new NotFoundException();
        }
    }
    @Override
    public void setViewedSeason(long seasonId) throws UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        seriesDao.setViewedSeason(seasonId,user.getId());
    }

    @Override
    public void rateSeries(long seriesId, double rating) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.rateSeries(seriesId, user.getId(), rating);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void unviewEpisode(long episodeId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.unviewEpisode(user.getId(), episodeId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }
    @Override
    public void unviewSeason(long seasonId) throws UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        if(user == null){
            throw new UnauthorizedException();
        }
        seriesDao.unviewSeason(seasonId,user.getId());
    }
    @Override
    public void addSeriesReview(String body, long seriesId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.addSeriesReview(body, seriesId, user.getId());
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void likePost(long postId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.likePost(user.getId(), postId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void unlikePost(long postId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.unlikePost(user.getId(), postId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void addCommentToPost(long commentPostId, String commentBody) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.addCommentToPost(commentPostId, commentBody, user.getId());
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void likeComment(long commentId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.likeComment(user.getId(), commentId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void unlikeComment(long commentId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.unlikeComment(user.getId(), commentId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void removeComment(long commentId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        if((!user.getIsAdmin() && seriesDao.getCommentAuthorId(commentId) != user.getId())) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.removeComment(commentId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void removePost(long postId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        if((!user.getIsAdmin() && seriesDao.getPostAuthorId(postId) != user.getId())) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.removePost(postId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Series> getWatchList() throws UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        List<Series> seriesList = seriesDao.getNextToBeSeen(user.getId());
        return seriesList;
    }

    @Override
    public List<Series> getRecentlyWatchedList(int number) throws UnauthorizedException, BadRequestException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        if(number <= 0) {
            throw new BadRequestException();
        }
        List<Series> seriesList = seriesDao.getRecentlyWatched(user.getId(), number);
        return seriesList;
    }

    @Override
    public List<Series> getAddedSeries() throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser();
        if(user == null) {
            throw new UnauthorizedException();
        }
        List<Series> seriesList = seriesDao.getAddedSeries(user.getId()).orElseThrow(NotFoundException::new);
        return seriesList;
    }

    @Override
    public List<Series> getAddedSeries(long userId) throws NotFoundException {
        List<Series> seriesList = seriesDao.getAddedSeries(userId).orElseThrow(NotFoundException::new);
        return seriesList;
    }
}
