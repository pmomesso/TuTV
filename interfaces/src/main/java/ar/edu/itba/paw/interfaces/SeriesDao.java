package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface SeriesDao {

    List<Series> searchSeries(String seriesName, String genreName, String networkName, int page, int pageSize);
    List<Series> getSeriesByName(String seriesName);

    List<Series> getSeriesByGenre(String genreName);
    List<Series> getSeriesByGenre(long id);

    List<Series> getBestSeriesByGenre(int genreId, int lowerLimit, int upperLimit);

    List<Genre> getAllGenres();
    List<Network> getAllNetworks();

    List<Series> getNewSeries(int lowerLimit, int upperLimit);

    Map<Genre,List<Series>> getBestSeriesByGenres();
    Map<Genre,List<Series>> getBestSeriesByGenres(Long id, Long page, Integer pageSize);

    Optional<Series> getSeriesById(long id);

    Optional<Series> createSeries(Integer tvdbid, String seriesName, String seriesDescription, Double userRating, String status, Integer runtime,
                      long networkId, String firstAired, String idImdb, String added, String updated, String posterUrl, String bannerUrl, Integer followers);

    long addSeriesGenre(String genre, List<Series> series);

    List<Season> getSeasonsBySeriesId(long seriesId);

    List<Episode> getEpisodesBySeasonId(long seasonId);

    List<Episode> getNextToBeSeen(long userId, int page, int pageSize);

    Optional<List<Series>> getRecentlyWatched(long userId, int number);

    Optional<List<Series>> getAddedSeries(long userId);

    Optional<List<Episode>> getUpcomingEpisodes(long userId);

    Optional<Genre> getGenreById(long genreId);

    boolean userFollows(long seriesId, long userId);
    Optional<Series> followSeries(long seriesId, long userId);
    Optional<Series> unfollowSeries(long seriesId, long userId);
    int setViewedEpisode(long seriesId,int seasonNumber, int episodeNumber, long userId);
    int setViewedSeason(long seriesId, int seasonNumber, long userId);
    int unviewSeason(long seriesId, int seasonNumber, long userId);
    int unviewEpisode(long userId, long seriesId,int seasonNumber, int episodeNumber);

    Optional<SeriesReview> createSeriesReview(String body, long seriesId, long userId, boolean isSpam);

    Optional<SeriesReview> likePost(long userId, long postId);

    Optional<SeriesReview> unlikePost(long userId, long postId);

    Optional<SeriesReviewComment> addCommentToPost(long commentPostId, String commentBody, long commentUserId);

    Optional<Notification> createNotification(User user, Series series, String message);

    Optional<SeriesReviewComment> likeComment(long userId, long commentId);

    Optional<SeriesReviewComment> unlikeComment(long userId, long commentId);

    int removeComment(long commentId);

    int removePost(long postId);

    Optional<Series> rateSeries(long seriesId, long userId, int rating);

    long getPostAuthorId(long postId);

    long getCommentAuthorId(long commentId);

    Optional<SeriesList> addList(long userId, String name, Set<Series> series);

    Optional<SeriesList> modifyList(long id, long userId, String name, Set<Series> series);

    int removeList(long id);

    Optional<SeriesReview> getSeriesReviewById(long commentPostId);

    boolean viewUntilEpisode(long episodeId, User u);

    List<Episode> getToBeReleasedEpisodes();

    Optional<Boolean> userLikesSeriesReview(User user, long seriesReviewId);

    Optional<SeriesReview> reviewWithComment(Long commentId);

    SeriesReviewComment getCommentById(Long commentId);

    Optional<Series> serieWithReview(Long seriesReviewId);

    int addSeriesToList(long id, long seriesId);
}
