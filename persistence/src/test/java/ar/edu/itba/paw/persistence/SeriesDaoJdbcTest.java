package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class SeriesDaoJdbcTest {

    private static final long ID = 1;
    private static final int TVDB_ID = 2;
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final double USER_RATING = 3;
    private static final String STATUS = "status";
    private static final int RUNTIME = 4;
    private static final String FIRST_AIRED = "2000-01-01";
    private static final String ID_IMDB = "5";
    private static final String ADDED = "2000-01-01";
    private static final String UPDATED = "2000-01-01";
    private static final String POSTER_URL = "url/poster";
    private static final String BANNER_URL = "url/banner";
    private static final int FOLLOWERS = 6;
    private static final int GENRE_ID = 7;
    private static final String GENRE = "genre";
    private static final Genre GENRE_OBJ = new Genre(GENRE_ID,GENRE);
    private static final String NETWORK_NAME = "network";
    private static final int NETWORK_ID = 8;
    private static final int SEASON_ID = 9;
    private static final int EPISODE_ID = 10;
    private static final int USER_ID = 11;

    @Autowired
    private DataSource ds;
    @Autowired
    private SeriesDaoJdbc seriesDao;
    private JdbcTemplate jdbcTemplate;

    private void populateDatabase(){
        jdbcTemplate.execute(String.format(Locale.US, "INSERT INTO network (networkid, name) VALUES(%d,'%s')", NETWORK_ID, NETWORK_NAME));
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO genres (id,genre) VALUES(%d,'%s')",GENRE_ID,GENRE));
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO series " +
                "(id,tvDbId,name,description,userRating,status,runtime,networkid,firstaired,id_imdb,added,updated,posterUrl,bannerUrl,followers) " +
                "VALUES(%d,%d,'%s','%s',%f,'%s',%d,%d,'%s','%s','%s','%s','%s','%s',%d)",
                ID, TVDB_ID, NAME,DESCRIPTION,USER_RATING,STATUS,RUNTIME,NETWORK_ID,FIRST_AIRED,ID_IMDB,ADDED,UPDATED,POSTER_URL,BANNER_URL,FOLLOWERS));
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO hasgenre (seriesid,genreid) VALUES(%d,%d)",ID,GENRE_ID));
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO season (seasonid,seriesid,seasonnumber) VALUES(%d,%d,%d)",SEASON_ID,ID,1));
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO episode (id,name,seriesId,overview,numEpisode,tvdbid,seasonid)VALUES(%d,'%s',%d,'%s',%d,%d,%d)",
                EPISODE_ID, NAME,ID,DESCRIPTION,1,TVDB_ID,SEASON_ID));
    }
    private void insertUser(){
        final String username = "username";
        final String password = "password";
        final String mail = "mail@mail.com";
        final String confirmationKey = "confirmation_key";
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO users " +
                        "(id,username,password,mail,confirmation_key) " +
                        "VALUES(%d,'%s','%s','%s','%s')",
                USER_ID, username, password,mail,confirmationKey));
    }
    private void assertSeries(Series series){
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getId(),ID);
        Assert.assertEquals(series.getName(),NAME);
        Assert.assertEquals(series.getSeriesDescription(),DESCRIPTION);
        Assert.assertEquals(series.getUserRating(),USER_RATING,0);
        Assert.assertEquals(series.getStatus(),STATUS);
        Assert.assertEquals(series.getRunningTime(),RUNTIME);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Assert.assertEquals(format.format(series.getFirstAired()),FIRST_AIRED);
        Assert.assertEquals(series.getImdbId(),ID_IMDB);
        Assert.assertEquals(format.format(series.getAdded()),ADDED);
        Assert.assertEquals(format.format(series.getUpdated()),UPDATED);
        Assert.assertEquals(series.getPosterUrl(),POSTER_URL);
        Assert.assertEquals(series.getBannerUrl(),BANNER_URL);
        Assert.assertEquals(series.getNumFollowers(),FOLLOWERS);
        Assert.assertEquals(series.getGenres().size(),1);
        Assert.assertEquals(series.getNetwork(), NETWORK_NAME);
        Genre g = (Genre)series.getGenres().toArray()[0];
        Assert.assertEquals(g.getId(),GENRE_ID);
        Assert.assertEquals(g.getName(),GENRE);
    }
    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "hasgenre");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "hasviewedepisode");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "follows");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "episode");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "season");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "series");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "genres");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "network");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void createSeriesTest(){
        //Ejercitar
        final long id = seriesDao.createSeries(TVDB_ID,NAME,DESCRIPTION,USER_RATING,STATUS,RUNTIME,null,FIRST_AIRED,
                ID_IMDB,ADDED,UPDATED,POSTER_URL,BANNER_URL,FOLLOWERS);
        //Asserts
        Assert.assertTrue(id >= 0);
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"series","id = " + id));
    }

    @Test
    public void getSeriesByIdTest() {
        //Setup
        populateDatabase();
        //Ejercitar
        final Series series = seriesDao.getSeriesById(ID,0);
        //Asserts
        assertSeries(series);
    }

    @Test
    public void getSeriesByNameTest() {
        //Setup
        populateDatabase();
        //Ejercitar
        final List<Series> seriesList = seriesDao.getSeriesByName(NAME);
        //Asserts
        Assert.assertNotNull(seriesList);
        Assert.assertEquals(1,seriesList.size());
        final Series series = seriesList.get(0);
        assertSeries(series);
    }
    @Test
    public void getSeriesByGenreNameTest() {
        //Setup
        populateDatabase();
        //Ejercitar
        final List<Series> seriesList = seriesDao.getSeriesByGenre(GENRE);
        //Asserts
        Assert.assertNotNull(seriesList);
        Assert.assertEquals(1,seriesList.size());
        final Series series = seriesList.get(0);
        assertSeries(series);
    }
    @Test
    public void getSeriesByGenreIdTest() {
        //Setup
        populateDatabase();
        //Ejercitar
        final List<Series> seriesList = seriesDao.getSeriesByGenre(GENRE_ID);
        //Asserts
        Assert.assertNotNull(seriesList);
        Assert.assertEquals(1,seriesList.size());
        final Series series = seriesList.get(0);
        assertSeries(series);
    }
    @Test
    public void getBestSeriesByGenreTest() {
        //Setup
        populateDatabase();
        //Ejercitar
        final List<Series> seriesList = seriesDao.getBestSeriesByGenre(GENRE_ID,0,5);
        //Asserts
        Assert.assertNotNull(seriesList);
        Assert.assertEquals(1,seriesList.size());
        final Series series = seriesList.get(0);
        assertSeries(series);
    }
    @Test
    public void getBestSeriesByGenresTest() {
        //Setup
        populateDatabase();
        //Ejercitar
        final Map<Genre,List<Series>> genres = seriesDao.getBestSeriesByGenres(0,5);
        //Asserts
        Assert.assertNotNull(genres);
        Assert.assertEquals(1,genres.size());
        Assert.assertTrue(genres.containsKey(GENRE_OBJ));
        Assert.assertEquals(1,genres.get(GENRE_OBJ).size());
        final Series series = genres.get(GENRE_OBJ).get(0);
        assertSeries(series);
    }
    @Test
    public void getNewSeriesTest() {
        //Setup
        populateDatabase();
        //Ejercitar
        final List<Series> seriesList = seriesDao.getNewSeries(0,5);
        //Asserts
        Assert.assertNotNull(seriesList);
        Assert.assertEquals(1,seriesList.size());
        final Series series = seriesList.get(0);
        assertSeries(series);
    }
    @Test
    public void addSeriesGenreTest() {
        //Setup
        Series s = new Series(ID, TVDB_ID, NAME,DESCRIPTION,null,POSTER_URL,BANNER_URL,USER_RATING,STATUS,RUNTIME,FOLLOWERS,ID_IMDB,FIRST_AIRED,ADDED,UPDATED);
        List<Series> genreSeries = new ArrayList<>();
        genreSeries.add(s);
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO series " +
                        "(id,tvDbId,name,description,userRating,status,runtime,firstaired,id_imdb,added,updated,posterUrl,bannerUrl,followers) " +
                        "VALUES(%d,%d,'%s','%s',%f,'%s',%d,'%s','%s','%s','%s','%s','%s',%d)",
                ID, TVDB_ID, NAME,DESCRIPTION,USER_RATING,STATUS,RUNTIME,FIRST_AIRED,ID_IMDB,ADDED,UPDATED,POSTER_URL,BANNER_URL,FOLLOWERS));
        //Ejercitar
        final long id = seriesDao.addSeriesGenre(GENRE,genreSeries);
        //Asserts
        Assert.assertTrue(id >= 0);
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"genres","id = " + id));
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"hasgenre","seriesid = " + ID + " AND genreid = " + id));
    }
    @Test
    public void setSeriesRunningTimeTest(){
        //Setup
        populateDatabase();
        final int newRuntime = RUNTIME + 1;
        //Ejercitar
        seriesDao.setSeriesRunningTime(ID,newRuntime);
        //Asserts
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"series","runtime = " + newRuntime));
    }
    @Test
    public void setSeriesNetworkTest(){
        //Setup
        populateDatabase();
        //Ejercitar
        seriesDao.setSeriesNetwork(ID,NETWORK_ID);
        //Asserts
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"series","networkId = " + NETWORK_ID));
    }
    @Test
    public void setSeriesDescriptionTest(){
        //Setup
        populateDatabase();
        final String newDescription = "new description";
        //Ejercitar
        seriesDao.setSeriesDescription(ID,newDescription);
        //Asserts
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"series","description = '" + newDescription + "'"));
    }
    @Test
    public void followSeriesTest(){
        //Setup
        populateDatabase();
        insertUser();
        //Ejercitar
        seriesDao.followSeries(ID,USER_ID);
        //Asserts
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"follows"));
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"series","followers="+(FOLLOWERS + 1)));
    }
    @Test
    public void setViewedEpisodeTest(){
        //Setup
        populateDatabase();
        insertUser();
        //Ejercitar
        seriesDao.setViewedEpisode(EPISODE_ID,USER_ID);
        //Asserts
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"hasviewedepisode"));
    }
    @Test
    public void searchSeriesTest(){
        //Setup
        populateDatabase();
        //Ejercitar
        List<Series> series = seriesDao.searchSeries(NAME,GENRE,NETWORK_NAME,0,(int)USER_RATING + 1);
        //Asserts
        Assert.assertEquals(1,series.size());
        assertSeries(series.get(0));
    }
    @Test
    public void getSeasonsBySeriesIdTest(){
        //Setup
        populateDatabase();
        //Ejercitar
        List<Season> seasons = seriesDao.getSeasonsBySeriesId(ID);
        //Asserts
        Assert.assertEquals(1,seasons.size());
        Season s = seasons.get(0);
        Assert.assertEquals(SEASON_ID,s.getId());
        Assert.assertEquals(1,s.getSeasonNumber());
    }
    @Test
    public void getEpisodesBySeasonIdTest(){
        //Setup
        populateDatabase();
        insertUser();
        //Ejercitar
        List<Episode> episodes = seriesDao.getEpisodesBySeasonId(SEASON_ID,USER_ID);
        //Asserts
        Assert.assertEquals(1,episodes.size());
        Episode e = episodes.get(0);
        Assert.assertEquals(EPISODE_ID,e.getId());
        Assert.assertEquals(NAME,e.getName());
        Assert.assertEquals(DESCRIPTION,e.getDescription());
        Assert.assertEquals(1,e.getEpisodeNumber());
    }
    @Test
    public void getSeriesCommentsByIdTest(){
        //Setup
        populateDatabase();
        insertUser();
        long commentId = 1;
        long numLikes = 1;
        String body = "body";
        jdbcTemplate.execute(String.format("INSERT INTO seriesreview (id,userid,body,seriesid,numLikes) VALUES (%d,%d,'%s',%d,%d)",
                commentId,USER_ID,body,ID,numLikes));
        //Ejercitar
        List<Comment> comments = seriesDao.getSeriesCommentsById(ID);
        //Asserts
        Assert.assertEquals(1,comments.size());
        Comment c = comments.get(0);
        Assert.assertEquals(commentId,c.getCommentId());
        Assert.assertEquals(USER_ID,c.getUserId());
        Assert.assertEquals(numLikes,c.getPoints());
        Assert.assertEquals(body,c.getBody());
        Assert.assertNotNull(c.getUser());
        Assert.assertEquals(USER_ID,c.getUser().getId());
    }
    @Test
    public void getNextToBeSeenTest(){
        //Setup
        populateDatabase();
        insertUser();
        jdbcTemplate.execute(String.format("INSERT INTO follows (userid,seriesid) VALUES (%d,%d)", USER_ID,ID));
        //TODO
        //Ejercitar
//      List<Series> series = seriesDao.getNextToBeSeen(USER_ID);
        //Asserts
//      Assert.assertEquals(1,series.size());
//      assertSeries(series.get(0));
    }
    @Test
    public void getAllGenresTest(){
        //Setup
        populateDatabase();
        //Ejercitar
        List<Genre> genres = seriesDao.getAllGenres();
        //Asserts
        Assert.assertEquals(1,genres.size());
        Genre g = genres.get(0);
        Assert.assertEquals(GENRE,g.getName());
        Assert.assertEquals(GENRE_ID,g.getId());
    }
    @Test
    public void getSeriesByWrongId(){
        //Setup
        populateDatabase();
        //Ejercitar
        final Series series = seriesDao.getSeriesById(ID + 1, -1);
        //Asserts
        Assert.assertNull(series);
    }
    @Test
    public void getSeriesByWrongGenreId(){
        //Setup
        populateDatabase();
        //Ejercitar
        final List<Series> series = seriesDao.getSeriesByGenre(GENRE_ID + 1);
        //Asserts
        Assert.assertEquals(series.size(),0);
    }
    @Test
    public void getSeriesByWrongGenreName(){
        //Setup
        populateDatabase();
        //Ejercitar
        final List<Series> series = seriesDao.getSeriesByGenre(GENRE + "new");
        //Asserts
        Assert.assertEquals(series.size(),0);
    }
    @Test
    public void getBestSeriesByWrongGenre(){
        //Setup
        populateDatabase();
        //Ejercitar
        final List<Series> series = seriesDao.getBestSeriesByGenre(GENRE_ID + 1,0,5);
        //Asserts
        Assert.assertEquals(series.size(),0);
    }
    @Test
    public void getBestSeriesByOutsideLimits(){
        //Setup
        populateDatabase();
        //Ejercitar
        final List<Series> series = seriesDao.getBestSeriesByGenre(GENRE_ID + 1,3,9);
        //Asserts
        Assert.assertEquals(series.size(),0);
    }
    @Test
    public void getNewSeriesByOutsideLimits(){
        //Setup
        populateDatabase();
        //Ejercitar
        final List<Series> series = seriesDao.getNewSeries(3,9);
        //Asserts
        Assert.assertEquals(series.size(),0);
    }
    @Test
    public void followSeriesByWrongSeriesIdTest(){
        //Setup
        populateDatabase();
        insertUser();
        //Ejercitar
        try {
            seriesDao.followSeries(ID + 1, USER_ID);
        }catch(DataIntegrityViolationException e){
            //Asserts
            Assert.assertEquals(0,JdbcTestUtils.countRowsInTable(jdbcTemplate,"follows"));
            Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"series","followers="+(FOLLOWERS)));
            return;
        }
        //Si no lanzo una excepcion, falla el test.
        Assert.fail();
    }
    @Test
    public void searchSeriesByWrongNameTest(){
        //Setup
        populateDatabase();
        //Ejercitar
        List<Series> series = seriesDao.searchSeries(NAME + "extra",GENRE,NETWORK_NAME,0,(int)USER_RATING + 1);
        //Asserts
        Assert.assertEquals(0,series.size());
    }
    @Test
    public void searchSeriesByWrongGenreTest(){
        //Setup
        populateDatabase();
        //Ejercitar
        List<Series> series = seriesDao.searchSeries(NAME,GENRE + "extra",NETWORK_NAME,0,(int)USER_RATING + 1);
        //Asserts
        Assert.assertEquals(0,series.size());
    }
    @Test
    public void searchSeriesByWrongNetworkTest(){
        //Setup
        populateDatabase();
        //Ejercitar
        List<Series> series = seriesDao.searchSeries(NAME,GENRE,NETWORK_NAME + "extra",0,(int)USER_RATING + 1);
        //Asserts
        Assert.assertEquals(0,series.size());
    }
    @Test
    public void setViewedEpisodeWithWrongIdTest(){
        //Setup
        populateDatabase();
        insertUser();
        //Ejercitar
        try {
            seriesDao.setViewedEpisode(EPISODE_ID + 1, USER_ID);
        }catch(DataIntegrityViolationException e){
            //Asserts
            Assert.assertEquals(0,JdbcTestUtils.countRowsInTable(jdbcTemplate,"hasviewedepisode"));
            return;
        }
        //Si no lanzo excepcion, falla el test.
        Assert.fail();
    }
    @Test
    public void getSeasonsByWrongSeriesIdTest(){
        //Setup
        populateDatabase();
        //Ejercitar
        List<Season> seasons = seriesDao.getSeasonsBySeriesId(ID + 1);
        //Asserts
        Assert.assertEquals(0,seasons.size());
    }
    @Test
    public void getEpisodesByWrongSeasonIdTest(){
        //Setup
        populateDatabase();
        insertUser();
        //Ejercitar
        List<Episode> episodes = seriesDao.getEpisodesBySeasonId(SEASON_ID + 1,USER_ID);
        //Asserts
        Assert.assertEquals(0,episodes.size());
    }
}
