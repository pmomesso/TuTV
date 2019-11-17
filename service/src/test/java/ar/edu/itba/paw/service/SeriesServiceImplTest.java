package ar.edu.itba.paw.service;


import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.ApiException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class SeriesServiceImplTest {

    private static final long ID = 1;
    private static final int TVDB_ID = 2;
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final double TOTAL_RATING = 3.4;
    private static final String STATUS = "status";
    private static final int RUNTIME = 4;
    private static final String FIRST_AIRED = "2000-01-01";
    private static final String ID_IMDB = "5";
    private static final String ADDED = "2000-01-01";
    private static final String UPDATED = "2000-01-01";
    private static final String POSTER_URL = "url/poster";
    private static final String BANNER_URL = "url/banner";
    private static final int FOLLOWERS = 6;
    private static final long GENRE_ID = 7;
    private static final String GENRE = "genre";
    private static final String NETWORK_NAME = "network";
    private static final long NETWORK_ID = 8;
    private static final long SERIES_ID = 9;
    private static final long SEASON_ID = 10;
    private static final long EPISODE_ID = 11;
    private static final long USER_ID = 12;

    @Mock
    private SeriesDao mockDao;

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private SeriesServiceImpl seriesService = new SeriesServiceImpl(mockDao,mockUserService);

    private User getMockUser() {
        User u = new User();
        u.setId(USER_ID);
        return u;
    }
    private Series getMockSeries(){
        Genre g = new Genre(GENRE_ID,GENRE);
        Series s = new Series(ID,TVDB_ID,NAME,DESCRIPTION,new Network(NETWORK_ID,NETWORK_NAME),POSTER_URL,BANNER_URL,TOTAL_RATING,STATUS,RUNTIME,FOLLOWERS,ID_IMDB,FIRST_AIRED,ADDED,UPDATED);
        s.addGenre(g);
        return s;
    }
    private void assertMockSeries(Series series){
        Assert.assertNotNull(series);
        Assert.assertEquals(ID,series.getId());
        Assert.assertEquals(NAME,series.getName());
        Assert.assertEquals(DESCRIPTION,series.getSeriesDescription());
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
        Assert.assertEquals(1,series.getGenres().size());
        Assert.assertEquals(NETWORK_NAME,series.getNetwork().getName());
        Genre g = (Genre)series.getGenres().toArray()[0];
        Assert.assertEquals(GENRE_ID,g.getId().longValue());
        Assert.assertEquals(GENRE,g.getName());
    }
    @Test
    public void searchSeriesByNameTest(){
        //Setup
        Series s = getMockSeries();
        Mockito.when(mockDao.searchSeries(Mockito.eq(NAME),Mockito.eq(GENRE),Mockito.eq(NETWORK_NAME),0)).thenAnswer(invocation -> {
            List<Series> seriesList = new ArrayList<>();
            seriesList.add(s);
            return seriesList;
        });
        //Ejercitar
        List<Series> seriesList = seriesService.searchSeries(NAME,GENRE,NETWORK_NAME,0);
        //Asserts
        Assert.assertEquals(1,seriesList.size());
        assertMockSeries(seriesList.get(0));
    }
    @Test
    public void getSeriesByIdTest(){
        //Setup
        Series s = getMockSeries();
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.getSeriesById(Mockito.eq(ID))).thenReturn(Optional.of(s));
        //Ejercitar
        Optional<Series> series = seriesService.getSerieById(ID);
        //Asserts
        Assert.assertTrue(series.isPresent());
        assertMockSeries(series.get());
    }
    @Test
    public void followSeriesTest(){
        //Setup
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.followSeries(Mockito.eq(ID),Mockito.eq(USER_ID))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.followSeries(ID);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void setViewedEpisodeTest(){
        //Setup
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.setViewedEpisode(Mockito.eq(EPISODE_ID),Mockito.eq(USER_ID))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.setViewedEpisode(SERIES_ID, EPISODE_ID);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void setViewedSeasonTest(){
        //Setup
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.setViewedSeason(Mockito.eq(SEASON_ID),Mockito.eq(USER_ID))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.setViewedSeason(SERIES_ID, SEASON_ID);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void rateSeriesTest(){
        //Setup
        final int rating = 3;
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.rateSeries(Mockito.eq(ID),Mockito.eq(USER_ID),Mockito.eq(rating))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.rateSeries(ID,rating);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void unviewEpisodeTest(){
        //Setup
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.unviewEpisode(Mockito.eq(USER_ID),Mockito.eq(EPISODE_ID))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.unviewEpisode(EPISODE_ID);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void unviewSeasonTest(){
        //Setup
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.unviewSeason(Mockito.eq(SEASON_ID),Mockito.eq(USER_ID))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.unviewSeason(SEASON_ID);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void addSeriesReviewTest(){
        //Setup
        final String body = "body";
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.createSeriesReview(Mockito.eq(body),Mockito.eq(ID),Mockito.eq(USER_ID),Mockito.eq(false))).thenReturn(Optional.of(new SeriesReview()));
        //Ejercitar
        try {
            seriesService.addSeriesReview(body,ID, false);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void likePostTest(){
        //Setup
        final long postId = 1;
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.likePost(Mockito.eq(USER_ID),Mockito.eq(postId))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.likePost(postId);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void unlikePostTest(){
        //Setup
        final long postId = 1;
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.unlikePost(Mockito.eq(USER_ID),Mockito.eq(postId))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.unlikePost(postId);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void addCommentToPostTest(){
        //Setup
        final long postId = 1;
        final String body = "body";
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.addCommentToPost(Mockito.eq(postId),Mockito.eq(body),Mockito.eq(USER_ID))).thenReturn(Optional.of(new SeriesReviewComment()));
        //Ejercitar
        try {
            seriesService.addCommentToPost(postId,body,"baseUrl");
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void likeCommentTest(){
        //Setup
        final long commentId = 1;
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.likeComment(Mockito.eq(USER_ID),Mockito.eq(commentId))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.likeComment(commentId);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void unlikeCommentTest(){
        //Setup
        final long commentId = 1;
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.unlikeComment(Mockito.eq(USER_ID),Mockito.eq(commentId))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.unlikeComment(commentId);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void removeCommentTest(){
        //Setup
        final long commentId = 1;
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.getCommentAuthorId(Mockito.eq(commentId))).thenReturn(USER_ID);
        Mockito.when(mockDao.removeComment(Mockito.eq(commentId))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.removeComment(commentId);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void removePostTest(){
        //Setup
        final long postId = 1;
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.getPostAuthorId(Mockito.eq(postId))).thenReturn(USER_ID);
        Mockito.when(mockDao.removePost(Mockito.eq(postId))).thenReturn(1);
        //Ejercitar
        try {
            seriesService.removePost(postId);
        } catch (ApiException e) {
            Assert.fail();
        }
    }
    @Test
    public void getWatchlistTest(){
        //Setup
        final Series mockSeries = getMockSeries();
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.getNextToBeSeen(Mockito.eq(USER_ID))).thenAnswer(invocation -> {
            List<Episode> episodes = new ArrayList<>();
            episodes.add(new Episode());
            return episodes;
        });
        //Ejercitar
        List<Episode> series;
        try {
            series = seriesService.getWatchList();
        } catch (ApiException e) {
            Assert.fail();
            return;
        }
        Assert.assertEquals(1,series.size());
    }
    @Test
    public void getRecentlyWatchedlistTest(){
        //Setup
        final Series mockSeries = getMockSeries();
        final int number = 1;
        Mockito.when(mockUserService.getLoggedUser()).thenReturn(Optional.of(getMockUser()));
        Mockito.when(mockDao.getRecentlyWatched(Mockito.eq(USER_ID),Mockito.eq(number))).thenAnswer(invocation -> {
            List<Series> series = new ArrayList<>();
            series.add(mockSeries);
            return Optional.of(series);
        });
        //Ejercitar
        List<Series> series;
        try {
            series = seriesService.getRecentlyWatchedList(number);
        } catch (ApiException e) {
            Assert.fail();
            return;
        }
        Assert.assertEquals(1,series.size());
        assertMockSeries(series.get(0));
    }
}
