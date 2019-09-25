package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.text.ParseException;
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
    @Autowired
    private DataSource ds;
    @Autowired
    private SeriesDaoJdbc seriesDao;
    private JdbcTemplate jdbcTemplate;

    private void populateDatabase(){
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO genres (id,genre) VALUES(%d,'%s')",GENRE_ID,GENRE));
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO series " +
                "(id,tvDbId,name,description,userRating,status,runtime,firstaired,id_imdb,added,updated,posterUrl,bannerUrl,followers) " +
                "VALUES(%d,%d,'%s','%s',%f,'%s',%d,'%s','%s','%s','%s','%s','%s',%d)",
                ID, TVDB_ID, NAME,DESCRIPTION,USER_RATING,STATUS,RUNTIME,FIRST_AIRED,ID_IMDB,ADDED,UPDATED,POSTER_URL,BANNER_URL,FOLLOWERS));
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO hasgenre (seriesid,genreid) VALUES(%d,%d)",ID,GENRE_ID));
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
        Genre g = (Genre)series.getGenres().toArray()[0];
        Assert.assertEquals(g.getId(),GENRE_ID);
        Assert.assertEquals(g.getName(),GENRE);
    }
    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "hasgenre");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "series");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "genres");
    }

    @Test
    public void createSeriesTest(){
        //Ejercitar
        final long id = seriesDao.createSeries(TVDB_ID,NAME,DESCRIPTION,USER_RATING,STATUS,RUNTIME,null,FIRST_AIRED,
                ID_IMDB,ADDED,UPDATED,POSTER_URL,BANNER_URL,FOLLOWERS);
        //Asserts
        Assert.assertTrue(id >= 0);
        Assert.assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"series","id = " + id),1);
    }

    @Test
    public void getSeriesByIdTest() {
        //Setup
        populateDatabase();
        //Ejercitar
        final Series series = seriesDao.getSeriesById(ID);
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
        Assert.assertEquals(seriesList.size(),1);
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
        Assert.assertEquals(seriesList.size(),1);
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
        Assert.assertEquals(seriesList.size(),1);
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
        Assert.assertEquals(seriesList.size(),1);
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
        Assert.assertEquals(genres.size(),1);
        Assert.assertTrue(genres.containsKey(GENRE_OBJ));
        Assert.assertEquals(genres.get(GENRE_OBJ).size(),1);
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
        Assert.assertEquals(seriesList.size(),1);
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
        Assert.assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"genres","id = " + id),1);
        Assert.assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"hasgenre","seriesid = " + ID + " AND genreid = " + id),1);
    }
    @Test
    public void setSeriesRunningTimeTest(){
        //Setup
        populateDatabase();
        final int newRuntime = RUNTIME + 1;
        //Ejercitar
        seriesDao.setSeriesRunningTime(ID,newRuntime);
        //Asserts
        Assert.assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"series","runtime = " + newRuntime),1);
    }
    @Test
    public void setSeriesNetworkTest(){
        //Setup
        populateDatabase();
        final int networkId = 1;
        jdbcTemplate.execute(String.format("INSERT INTO network (networkid,name) VALUES(%d,'%s')",networkId,"network"));
        //Ejercitar
        seriesDao.setSeriesNetwork(ID,networkId);
        //Asserts
        Assert.assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"series","networkId = " + networkId),1);
    }
    @Test
    public void setSeriesDescriptionTest(){
        //Setup
        populateDatabase();
        final String newDescription = "new description";
        //Ejercitar
        seriesDao.setSeriesDescription(ID,newDescription);
        //Asserts
        Assert.assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"series","description = '" + newDescription + "'"),1);
    }
    @Test
    public void getSeriesByWrongId(){
        //Setup
        populateDatabase();
        //Ejercitar
        final Series series = seriesDao.getSeriesById(ID + 1);
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
}
