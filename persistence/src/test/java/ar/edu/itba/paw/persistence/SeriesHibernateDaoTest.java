package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class SeriesHibernateDaoTest {

    /* Valores test serie */
    private static final int TVDB_ID = 2;
    private static final String SERIES_NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final double TOTAL_RATING = 3;
    private static final String STATUS = "status";
    private static final int RUNTIME = 4;
    private static final String FIRST_AIRED = "2000-01-01";
    private static final String ID_IMDB = "5";
    private static final String ADDED = "2000-01-01";
    private static final String UPDATED = "2000-01-01";
    private static final String POSTER_URL = "url/poster";
    private static final String BANNER_URL = "url/banner";
    private static final int FOLLOWERS = 6;
    /* Valores test genero */
    private static final String GENRE_NAME = "genre";
    /* Valores test network */
    private static final String NETWORK_NAME = "network";
    /* Valores test temporada */
    private static final int SEASON_NUM = 1;
    private static final String SEASON_NAME = "season name";
    /* Valores test episodio */
    private static final int EPISODE_NUM = 1;
    private static final String EPISODE_NAME = "episode name";
    private static final String EPISODE_OVERVIEW = "episode overview";
    private static final String EPISODE_AIRED = "2100-01-01";
    /* Valores test usuario */
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MAIL = "test@test.com";
    /* Valores test review */
    private static final String REVIEW_BODY = "review body";
    /* Valores test comentario */
    private static final String COMMENT_BODY = "comment body";

    @Autowired
    private SeriesHibernateDao seriesDao;
    @PersistenceContext
    private EntityManager em;

    private Series series = new Series();
    private Genre genre = new Genre();
    private Network network = new Network();
    private Season season = new Season();
    private Episode episode = new Episode();
    private User user = new User();
    private SeriesReview review = new SeriesReview();
    private SeriesReviewComment comment = new SeriesReviewComment();

    private void assertSeries(Series series){
        Assert.assertNotNull(series);
        Assert.assertEquals(this.series.getId(),series.getId());
        Assert.assertEquals(SERIES_NAME,series.getName());
        Assert.assertEquals(DESCRIPTION,series.getSeriesDescription());
        Assert.assertEquals(TOTAL_RATING,series.getUserRating(),0);
        Assert.assertEquals(STATUS,series.getStatus());
        Assert.assertEquals(RUNTIME,series.getRuntime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Assert.assertEquals(FIRST_AIRED,format.format(series.getFirstAired()));
        Assert.assertEquals(ID_IMDB,series.getImdbId());
        Assert.assertEquals(ADDED,format.format(series.getAdded()));
        Assert.assertEquals(UPDATED,format.format(series.getUpdated()));
        Assert.assertEquals(POSTER_URL,series.getPosterUrl());
        Assert.assertEquals(BANNER_URL,series.getBannerUrl());
        Assert.assertEquals(FOLLOWERS,series.getFollowers());
        Assert.assertEquals(this.network.getId(),series.getNetwork().getId());
        Assert.assertEquals(NETWORK_NAME,series.getNetwork().getName());
        Assert.assertEquals(1,series.getGenres().size());
        Genre g = (Genre)series.getGenres().toArray()[0];
        Assert.assertEquals(this.genre.getId(),g.getId());
        Assert.assertEquals(GENRE_NAME,g.getName());
        Assert.assertEquals(1,series.getSeasons().size());
        Season s = (Season)series.getSeasons().toArray()[0];
        Assert.assertEquals(season.getId(),s.getId());
        Assert.assertEquals(SEASON_NUM,s.getSeasonNumber());
        Assert.assertEquals(1,s.getEpisodes().size());
        Episode e = (Episode)s.getEpisodes().toArray()[0];
        assertEpisode(e);
    }
    private void assertEpisode(Episode e){
        Assert.assertNotNull(e);
        Assert.assertEquals(episode.getId(),e.getId());
        Assert.assertEquals(EPISODE_NUM,e.getNumEpisode());
        Assert.assertEquals(EPISODE_NAME,e.getName());
        Assert.assertEquals(EPISODE_OVERVIEW,e.getOverview());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Assert.assertEquals(EPISODE_AIRED,format.format(e.getAiring()));
    }
    private void insertUser(){
        user = new User(USERNAME,PASSWORD,MAIL,false);
        em.persist(user);
    }
    private void insertReview(){
        review = new SeriesReview(REVIEW_BODY,series,user);
        em.persist(review);
        user.getSeriesReviews().add(review);
        series.getSeriesReviewList().add(review);
    }
    private void insertComment(){
        comment = new SeriesReviewComment(COMMENT_BODY,review,user);
        em.persist(comment);
        user.getSeriesReviewComments().add(comment);
        review.getComments().add(comment);
    }
    @Before
    public void populateDatabase(){
        network = new Network(NETWORK_NAME);
        genre =  new Genre(GENRE_NAME);
        episode = new Episode(EPISODE_NAME,EPISODE_OVERVIEW,EPISODE_NUM,EPISODE_AIRED);
        season = new Season(SEASON_NAME,SEASON_NUM);
        season.addEpisode(episode);
        series =  new Series(TVDB_ID, SERIES_NAME,DESCRIPTION, network,POSTER_URL,BANNER_URL,TOTAL_RATING,STATUS,RUNTIME,FOLLOWERS,ID_IMDB,FIRST_AIRED,ADDED,UPDATED);
        series.addGenre(genre);
        series.addSeason(season);
        em.persist(series);
    }
    @Test
    public void createSeriesTest(){
        network = new Network(NETWORK_NAME);
        em.persist(network);
        em.flush();
        Optional<Series> s = seriesDao.createSeries(TVDB_ID,SERIES_NAME,DESCRIPTION,TOTAL_RATING,STATUS,RUNTIME,network.getId(),FIRST_AIRED,ID_IMDB,ADDED,UPDATED,POSTER_URL,BANNER_URL,FOLLOWERS);
        Assert.assertTrue(s.isPresent());
        series = s.get();
        Assert.assertEquals(this.series.getId(),series.getId());
        Assert.assertEquals(SERIES_NAME,series.getName());
        Assert.assertEquals(DESCRIPTION,series.getSeriesDescription());
        Assert.assertEquals(TOTAL_RATING,series.getUserRating(),0);
        Assert.assertEquals(STATUS,series.getStatus());
        Assert.assertEquals(RUNTIME,series.getRuntime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Assert.assertEquals(FIRST_AIRED,format.format(series.getFirstAired()));
        Assert.assertEquals(ID_IMDB,series.getImdbId());
        Assert.assertEquals(ADDED,format.format(series.getAdded()));
        Assert.assertEquals(UPDATED,format.format(series.getUpdated()));
        Assert.assertEquals(POSTER_URL,series.getPosterUrl());
        Assert.assertEquals(BANNER_URL,series.getBannerUrl());
        Assert.assertEquals(FOLLOWERS,series.getFollowers());
        Assert.assertEquals(this.network.getId(),series.getNetwork().getId());
        Assert.assertEquals(NETWORK_NAME,series.getNetwork().getName());
    }
    @Test
    public void getSeriesByIdTest(){
        Optional<Series> s = seriesDao.getSeriesById(series.getId());
        Assert.assertTrue(s.isPresent());
        assertSeries(s.get());
    }
    @Test
    public void getSeriesByGenreIdTest(){
        List<Series> s = seriesDao.getSeriesByGenre(genre.getId());
        Assert.assertEquals(1, s.size());
        assertSeries(s.get(0));
    }
    @Test
    public void getSeriesByGenreNameTest(){
        List<Series> s = seriesDao.getSeriesByGenre(GENRE_NAME);
        Assert.assertEquals(1, s.size());
        assertSeries(s.get(0));
    }
    @Test
    public void getSeriesByNameTest(){
        List<Series> s = seriesDao.getSeriesByName(SERIES_NAME);
        Assert.assertEquals(1, s.size());
        assertSeries(s.get(0));
    }
    @Test
    public void getBestSeriesByGenreTest(){
        Map<Genre,List<Series>> genreMap = seriesDao.getBestSeriesByGenres();
        Assert.assertEquals(1,genreMap.size());
        Genre g = (Genre)genreMap.keySet().toArray()[0];
        Assert.assertEquals(1,genreMap.get(g).size());
        Series s = genreMap.get(g).get(0);
        assertSeries(s);
    }
    @Test
    public void getNewSeriesTest(){
        List<Series> s = seriesDao.getNewSeries(0,(int)(TOTAL_RATING+1));
        Assert.assertEquals(1,s.size());
        assertSeries(s.get(0));
    }
    @Test
    public void searchSeriesByNameTest(){
        List<Series> series = seriesDao.searchSeries(SERIES_NAME,null,null,0);
        Assert.assertEquals(1,series.size());
        assertSeries(series.get(0));
    }
    @Test
    public void searchSeriesByGenreTest(){
        List<Series> series = seriesDao.searchSeries(null,GENRE_NAME,null,0);
        Assert.assertEquals(1,series.size());
        assertSeries(series.get(0));
    }
    @Test
    public void searchSeriesByNetworkTest(){
        List<Series> series = seriesDao.searchSeries(null,null,NETWORK_NAME,0);
        Assert.assertEquals(1,series.size());
        assertSeries(series.get(0));
    }
    @Test
    public void getAllGenresTest(){
        List<Genre> genres = seriesDao.getAllGenres();
        Assert.assertEquals(1,genres.size());
        Assert.assertEquals(genre.getId(),genres.get(0).getId());
        Assert.assertEquals(GENRE_NAME,genres.get(0).getName());
    }
    @Test
    public void getAllNetworksTest(){
        List<Network> networks = seriesDao.getAllNetworks();
        Assert.assertEquals(1,networks.size());
        Assert.assertEquals(network.getId(),networks.get(0).getId());
        Assert.assertEquals(NETWORK_NAME,networks.get(0).getName());
    }
    @Test
    public void getSeasonsBySeriesId(){
        List<Season> seasons = seriesDao.getSeasonsBySeriesId(series.getId());
        Assert.assertEquals(1,seasons.size());
        Assert.assertEquals(season.getId(),seasons.get(0).getId());
        Assert.assertEquals(SEASON_NUM,seasons.get(0).getSeasonNumber());
    }
    @Test
    public void getEpisodesBySeasonId(){
        List<Episode> episodes = seriesDao.getEpisodesBySeasonId(season.getId());
        Assert.assertEquals(1,episodes.size());
        assertEpisode(episodes.get(0));
    }
    @Test
    public void userFollowsTest(){
        insertUser();
        series.addUserFollower(user);
        boolean follows = seriesDao.userFollows(series.getId(),user.getId());
        Assert.assertTrue(follows);
    }
    @Test
    public void rateSeriesTest(){
        insertUser();
        series.setUserRating(0.0);
        int rating = 2;
        int updated = seriesDao.rateSeries(series.getId(),user.getId(),rating);
        Assert.assertEquals(1,updated);
        Assert.assertEquals(1,series.getRatings().size());
        Assert.assertEquals(1,user.getRatings().size());
        Assert.assertEquals(rating, series.getUserRating(),0.1);
    }
    @Test
    public void followSeriesTest(){
        insertUser();
        int updated = seriesDao.followSeries(series.getId(),user.getId());
        Assert.assertEquals(1,updated);
        Assert.assertEquals(FOLLOWERS + 1,series.getFollowers());
        Assert.assertEquals(1,series.getUserFollowers().size());
        Assert.assertEquals(1,user.getFollows().size());
    }
    @Test
    public void unfollowSeriesTest(){
        insertUser();
        series.addUserFollower(user);
        int updated = seriesDao.unfollowSeries(series.getId(),user.getId());
        Assert.assertEquals(1,updated);
        Assert.assertEquals(FOLLOWERS - 1,series.getFollowers());
        Assert.assertEquals(0,series.getUserFollowers().size());
        Assert.assertEquals(0,user.getFollows().size());
    }
    @Test
    public void getNextToBeSeenTest(){
        insertUser();
        series.addUserFollower(user);
        List<Episode> episodes = seriesDao.getNextToBeSeen(user.getId());
        Assert.assertEquals(1,episodes.size());
        assertEpisode(episodes.get(0));
    }
    @Test
    public void getAddedSeries(){
        insertUser();
        series.addUserFollower(user);
        Optional<List<Series>> series = seriesDao.getAddedSeries(user.getId());
        Assert.assertTrue(series.isPresent());
        Assert.assertEquals(1,series.get().size());
        assertSeries(series.get().get(0));
    }
    @Test
    public void getRecentlyWatched(){
        insertUser();
        episode.addViewer(user);
        Optional<List<Series>> seriesList = seriesDao.getRecentlyWatched(user.getId(),1);
        Assert.assertTrue(seriesList.isPresent());
        Assert.assertEquals(1,seriesList.get().size());
        assertSeries(seriesList.get().get(0));
    }
    @Test
    public void getUpcomingEpisodesTest(){
        insertUser();
        series.addUserFollower(user);
        Optional<List<Episode>> episodes = seriesDao.getUpcomingEpisodes(user.getId());
        Assert.assertTrue(episodes.isPresent());
        Assert.assertEquals(1,episodes.get().size());
        assertEpisode(episodes.get().get(0));
    }
    @Test
    public void setViewedEpisodeTest(){
        insertUser();
        int updated = seriesDao.setViewedEpisode(episode.getId(),user.getId());
        Assert.assertEquals(1,updated);
        Assert.assertEquals(1,episode.getViewers().size());
        Assert.assertEquals(1,user.getViewed().size());
    }
    @Test
    public void unviewEpisodeTest(){
        insertUser();
        episode.addViewer(user);
        int updated = seriesDao.unviewEpisode(user.getId(),episode.getId());
        Assert.assertEquals(1,updated);
        Assert.assertEquals(0,episode.getViewers().size());
        Assert.assertEquals(0,user.getViewed().size());
    }
    @Test
    public void setViewedSeasonTest(){
        insertUser();
        int updated = seriesDao.setViewedSeason(season.getId(),user.getId());
        Assert.assertEquals(1,updated);
        Assert.assertEquals(1,episode.getViewers().size());
        Assert.assertEquals(1,user.getViewed().size());
    }
    @Test
    public void unviewSeasonTest(){
        insertUser();
        for(Episode e : season.getEpisodes())
            e.addViewer(user);

        int updated = seriesDao.unviewSeason(season.getId(),user.getId());
        Assert.assertEquals(1,updated);
        Assert.assertEquals(0,episode.getViewers().size());
        Assert.assertEquals(0,user.getViewed().size());
    }
    @Test
    public void createSeriesReviewTest(){
        insertUser();
        Optional<SeriesReview> review = seriesDao.createSeriesReview(REVIEW_BODY,series.getId(),user.getId());
        Assert.assertTrue(review.isPresent());
        Assert.assertEquals(REVIEW_BODY,review.get().getBody());
        Assert.assertEquals(user.getId(),review.get().getUserId());
        Assert.assertEquals(1,user.getSeriesReviews().size());
        Assert.assertEquals(1,series.getSeriesReviewList().size());
        Assert.assertEquals(0,review.get().getNumLikes());
        assertSeries(review.get().getSeries());
    }
    @Test
    public void likePostTest(){
        insertUser();
        insertReview();
        int updated = seriesDao.likePost(user.getId(),review.getId());
        Assert.assertEquals(1,updated);
        Assert.assertEquals(1,review.getNumLikes());
        Assert.assertEquals(1,review.getLikes().size());
        Assert.assertEquals(1,user.getSeriesReviewLikes().size());
    }
    @Test
    public void unlikePostTest(){
        insertUser();
        insertReview();
        review.addLike(user);
        review.setNumLikes(1);
        int updated = seriesDao.unlikePost(user.getId(),review.getId());
        Assert.assertEquals(1,updated);
        Assert.assertEquals(0,review.getNumLikes());
        Assert.assertEquals(0,review.getLikes().size());
        Assert.assertEquals(0,user.getSeriesReviewLikes().size());
    }
    @Test
    public void addCommentToPostTest(){
        insertUser();
        insertReview();
        Optional<SeriesReviewComment> comment = seriesDao.addCommentToPost(review.getId(),COMMENT_BODY,user.getId());
        Assert.assertTrue(comment.isPresent());
        Assert.assertEquals(COMMENT_BODY,comment.get().getBody());
        Assert.assertEquals(0,comment.get().getNumLikes());
        Assert.assertEquals(user.getId(),comment.get().getUser().getId());
        Assert.assertEquals(review.getId(),comment.get().getParent().getId());
        Assert.assertEquals(1,user.getSeriesReviewComments().size());
        Assert.assertEquals(1,review.getComments().size());
    }
    @Test
    public void likeCommentTest(){
        insertUser();
        insertReview();
        insertComment();
        int updated = seriesDao.likeComment(user.getId(),comment.getId());
        Assert.assertEquals(1,updated);
        Assert.assertEquals(1,comment.getNumLikes());
        Assert.assertEquals(1,comment.getLikes().size());
        Assert.assertEquals(1,user.getSeriesReviewCommentLikes().size());
    }
    @Test
    public void unlikeCommentTest(){
        insertUser();
        insertReview();
        insertComment();
        comment.setNumLikes(1);
        comment.addLike(user);
        int updated = seriesDao.unlikeComment(user.getId(),comment.getId());
        Assert.assertEquals(1,updated);
        Assert.assertEquals(0,comment.getNumLikes());
        Assert.assertEquals(0,comment.getLikes().size());
        Assert.assertEquals(0,user.getSeriesReviewCommentLikes().size());
    }
}
