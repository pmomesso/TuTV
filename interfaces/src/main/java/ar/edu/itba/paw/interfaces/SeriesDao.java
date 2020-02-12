package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface SeriesDao {

    List<Series> searchSeries(String seriesName, String genreName, String networkName, int page);
    List<Series> getSeriesByName(String seriesName);

    List<Series> getSeriesByGenre(String genreName);
    List<Series> getSeriesByGenre(long id);

    List<Series> getBestSeriesByGenre(int genreId, int lowerLimit, int upperLimit);

    List<Genre> getAllGenres();
    List<Network> getAllNetworks();

    List<Series> getNewSeries(int lowerLimit, int upperLimit);

    Map<Genre,List<Series>> getBestSeriesByGenres();
    Map<Genre,List<Series>> getBestSeriesByGenres(Long id, Long page);

    Optional<Series> getSeriesById(long id);

    Optional<Series> createSeries(Integer tvdbid, String seriesName, String seriesDescription, Double userRating, String status, Integer runtime,
                      long networkId, String firstAired, String idImdb, String added, String updated, String posterUrl, String bannerUrl, Integer followers);

    long addSeriesGenre(String genre, List<Series> series);

    List<Season> getSeasonsBySeriesId(long seriesId);

    List<Episode> getEpisodesBySeasonId(long seasonId);

    List<Episode> getNextToBeSeen(long userId);

    Optional<List<Series>> getRecentlyWatched(long userId, int number);

    Optional<List<Series>> getAddedSeries(long userId);

    Optional<List<Episode>> getUpcomingEpisodes(long userId);

    Optional<Genre> getGenreById(long genreId);

    boolean userFollows(long seriesId, long userId);
    int followSeries(long seriesId, long userId);
    int unfollowSeries(long seriesId, long userId);
    int setViewedEpisode(long episodeId, long userId);
    int setViewedSeason(long seasonId, long userId);
    int unviewSeason(long seasonId, long userId);
    int unviewEpisode(long userId, long episodeId);

    Optional<SeriesReview> createSeriesReview(String body, long seriesId, long userId, boolean isSpam);

    int likePost(long userId, long postId);

    int unlikePost(long userId, long postId);

    Optional<SeriesReviewComment> addCommentToPost(long commentPostId, String commentBody, long commentUserId);

    Optional<Notification> createNotification(User user, Series series, String message);

    int likeComment(long userId, long commentId);

    int unlikeComment(long userId, long commentId);

    int removeComment(long commentId);

    int removePost(long postId);

    int rateSeries(long seriesId, long userId, int rating);

    long getPostAuthorId(long postId);

    long getCommentAuthorId(long commentId);

    void addList(long userId, String name, Set<Series> series);

    int modifyList(long id, long userId, String name, Set<Series> series);

    int removeList(long id);

    Optional<SeriesReview> getSeriesReviewById(long commentPostId);

    boolean viewUntilEpisode(long episodeId, User u);

    List<Episode> getToBeReleasedEpisodes();

    Optional<Boolean> userLikesSeriesReview(User user, long seriesReviewId);

    SeriesReview reviewWithComment(Long commentId);

    SeriesReviewComment getCommentById(Long commentId);

    Series serieWithReview(Long seriesReviewId);
}
