package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SeriesService {

    List<Series> searchSeries(String seriesName, String genreName, String networkName, int page, int pageSize);
    List<Series> getSeriesByName(String name);

    Optional<Series> getSerieById(long id);

    List<Series> getSeriesByGenreAndNumber(int genreId, int num);
    List<Series> getAllSeriesByGenre(String genreName);
    List<Series> getAllSeriesByGenre(long id);
    Map<Genre,List<Series>> getSeriesByGenre();
    Map<Genre,List<Series>> getSeriesByGenre(Long id, Long page, Integer pageSize);
    List<Series> getNewestSeries(int lowerNumber, int upperNumber);
    List<Season> getSeasonsBySeriesId(long seriesId);
    List<Genre> getAllGenres();

    Genre getGenreById(long genreId) throws NotFoundException;

    List<Network> getAllNetworks();

    boolean follows(long seriesId) throws UnauthorizedException;
    Optional<Series> followSeries(long seriesId) throws UnauthorizedException, NotFoundException;
    void unfollowSeries(long seriesId) throws NotFoundException, UnauthorizedException;
    void setViewedEpisode(long seriesId, int seasonNumber, int episodeNumber) throws NotFoundException, UnauthorizedException;
    void setViewedSeason(long seriesId, int seasonNumber) throws UnauthorizedException, NotFoundException;
    void rateSeries(long seriesId, int rating) throws NotFoundException, UnauthorizedException, BadRequestException;
    void unviewEpisode(long seriesId, int seasonNumber, int episodeNumber) throws NotFoundException, UnauthorizedException;
    void unviewSeason(long seriesId,int seasonNumber) throws UnauthorizedException, NotFoundException;
    Optional<SeriesReview> addSeriesReview(String body, long seriesId, boolean isSpam) throws UnauthorizedException;

    void likePost(long postId) throws NotFoundException, UnauthorizedException;

    void unlikePost(long postId) throws NotFoundException, UnauthorizedException;

    SeriesReviewComment addCommentToPost(long commentPostId, String body, String baseUrl) throws NotFoundException, UnauthorizedException;

    void viewUntilEpisode(long seriesId, long episodeId) throws NotFoundException, UnauthorizedException;

    void likeComment(long commentId) throws NotFoundException, UnauthorizedException;

    void unlikeComment(long commentId) throws NotFoundException, UnauthorizedException;

    void removeComment(long commentId) throws UnauthorizedException, NotFoundException;
    
    void removePost(long postId) throws UnauthorizedException, NotFoundException;

    List<Episode> getWatchList(int page, int pageSize) throws UnauthorizedException, NotFoundException;

    List<Series> getRecentlyWatchedList(int number) throws UnauthorizedException, BadRequestException;

    List<Series> getAddedSeries() throws NotFoundException, UnauthorizedException;
    List<Series> getAddedSeries(long userId) throws NotFoundException;

    List<Episode> getUpcomingEpisodes() throws UnauthorizedException, NotFoundException;

    Optional<SeriesList> addList(String name, long[] seriesId) throws UnauthorizedException;

    Optional<Boolean> getLoggedInUserLikesSeriesReview(long seriesReviewId);

    int addSeriesToList(long id, long seriesId) throws UnauthorizedException, NotFoundException;

    Optional<SeriesList> modifyList(long id, String name, long[] seriesId) throws UnauthorizedException;

    void removeList(long id) throws UnauthorizedException, NotFoundException;

    void createNotificationsForNewEpisodes();

    Optional<SeriesReview> getSeriesReviewById(Long seriesId);

    Optional<SeriesReview> reviewWithComment(Long commentId);

    Optional<SeriesReviewComment> getSeriesReviewCommentById(Long commentId);

    Optional<Series> serieWithReview(Long seriesReviewId);

}