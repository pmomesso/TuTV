package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Season;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;

import java.util.List;
import java.util.Map;

public interface SeriesService {

    List<Series> searchSeries(String seriesName,String genreName,String networkName,int minRating,int maxRating);
    List<Series> getSeriesByName(String name);

    Series getSerieById(long id);

    List<Series> getSeriesByGenreAndNumber(int genreId, int num);
    List<Series> getAllSeriesByGenre(String genreName);
    public List<Series> getAllSeriesByGenre(int id);
    Map<Genre, List<Series>> getSeriesByGenreMap(int lowerNumber, int upperNumber);
    List<Series> getNewestSeries(int lowerNumber, int upperNumber);
    List<Season> getSeasonsBySeriesId(long seriesId);
    List<Genre> getAllGenres();
    void followSeries(long seriesId);
    void setViewedEpisode(long episodeId);

    void rateSeries(long seriesId,double rating);
    void unviewEpisode(long episodeId);

    void addSeriesReview(String body, long seriesId);

    void likePost(long postId);

    void unlikePost(long postId);

    void addCommentToPost(long commentPostId, String commentBody);

    void likeComment(long commentId);

    void unlikeComment(long commentId);

    void removeComment(long commentId);
    
    void removePost(long postId);
}
