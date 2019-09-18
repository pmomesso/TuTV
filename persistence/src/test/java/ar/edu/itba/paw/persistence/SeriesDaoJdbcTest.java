package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.User;
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
import java.util.List;
import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class SeriesDaoJdbcTest {

    private static final long ID = 1;
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final float USER_RATING = 5;
    private static final String NETWORK = "network";
    private static final String STATUS = "status";
    private static final int RUNTIME = 1;

    @Autowired
    private DataSource ds;
    @Autowired
    private SeriesDaoJdbc seriesDao;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "series");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "genres");
    }

    @Test
    public void createSeriesTest(){
        //Ejercitar
        final long id = seriesDao.createSeries(NAME,DESCRIPTION);
        //Asserts
        Assert.assertTrue(id >= 0);
    }

    @Test
    public void getSeriesByIdTest() {
        //Setup
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO series VALUES(%d,'%s','%s',%f,'%s','%s',%d)", ID, NAME, DESCRIPTION,USER_RATING,NETWORK,STATUS,RUNTIME));
        //Ejercitar
        final Series series = seriesDao.getSeriesById(ID);
        //Asserts
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getSeriesName(),NAME);
        Assert.assertEquals(series.getSeriesDescription(),DESCRIPTION);
        Assert.assertEquals(series.getUserRating(),USER_RATING,0.0f);
    }

    @Test
    public void getSeriesByNameTest() {
        //Setup
        jdbcTemplate.execute(String.format(Locale.US,"INSERT INTO series VALUES(%d,'%s','%s',%f,'%s','%s',%d)", ID, NAME, DESCRIPTION,USER_RATING,NETWORK,STATUS,RUNTIME));
        //Ejercitar
        final List<Series> seriesList = seriesDao.getSeriesByName(NAME);
        //Asserts
        Assert.assertNotNull(seriesList);
        Assert.assertEquals(seriesList.size(),1);
        final Series series = seriesList.get(0);
        Assert.assertEquals(series.getSeriesName(),NAME);
        Assert.assertEquals(series.getSeriesDescription(),DESCRIPTION);
        Assert.assertEquals(series.getUserRating(),USER_RATING,0.0f);
    }
}
