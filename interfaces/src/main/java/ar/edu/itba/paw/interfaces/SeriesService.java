package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SeriesService {

    List<Series> searchSeries(String seriesName, String genreName, String networkName);
    List<Series> getSeriesByName(String name);

    Optional<Series> getSerieById(long id);

    List<Series> getSeriesByGenreAndNumber(int genreId, int num);
    List<Series> getAllSeriesByGenre(String genreName);
    List<Series> getAllSeriesByGenre(int id);
    List<Genre> getSeriesByGenre(int lowerNumber, int upperNumber);
    List<Series> getNewestSeries(int lowerNumber, int upperNumber);
    List<Season> getSeasonsBySeriesId(long seriesId);
    List<Genre> getAllGenres();
    List<Network> getAllNetworks();

    boolean follows(long seriesId) throws UnauthorizedException;
    void followSeries(long seriesId) throws NotFoundException, UnauthorizedException;
    void unfollowSeries(long seriesId) throws NotFoundException, UnauthorizedException;
    void setViewedEpisode(long episodeId) throws NotFoundException, UnauthorizedException;
    void setViewedSeason(long seasonId) throws UnauthorizedException, NotFoundException;
    void rateSeries(long seriesId, int rating) throws NotFoundException, UnauthorizedException, BadRequestException;
    void unviewEpisode(long episodeId) throws NotFoundException, UnauthorizedException;
    void unviewSeason(long seasonId) throws UnauthorizedException, NotFoundException;
    void addSeriesReview(String body, long seriesId) throws NotFoundException, UnauthorizedException;

    void likePost(long postId) throws NotFoundException, UnauthorizedException;

    void unlikePost(long postId) throws NotFoundException, UnauthorizedException;

    void addCommentToPost(long commentPostId, String commentBody) throws NotFoundException, UnauthorizedException;

    void likeComment(long commentId) throws NotFoundException, UnauthorizedException;

    void unlikeComment(long commentId) throws NotFoundException, UnauthorizedException;

    void removeComment(long commentId) throws UnauthorizedException, NotFoundException;
    
    void removePost(long postId) throws UnauthorizedException, NotFoundException;

    List<Episode> getWatchList() throws UnauthorizedException, NotFoundException;

    List<Series> getRecentlyWatchedList(int number) throws UnauthorizedException, BadRequestException, NotFoundException;

    List<Series> getAddedSeries() throws NotFoundException, UnauthorizedException;
    List<Series> getAddedSeries(long userId) throws NotFoundException;

    List<Episode> getUpcomingEpisodes() throws UnauthorizedException;
}