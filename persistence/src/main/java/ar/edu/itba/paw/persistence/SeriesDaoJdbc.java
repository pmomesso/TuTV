package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.model.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SeriesDaoJdbc implements SeriesDao {

    private RowMapper<Series> seriesRowMapper = (resultSet, i) -> {
        Series ret = new Series(resultSet.getString("seriesName"));
        String description = resultSet.getString("seriesDescription");
        if (description != null) {
            ret.setDescription(description);
        }
        Double userRating = resultSet.getDouble("userRating");
        if(userRating != null) {
            ret.setUserRating(userRating);
        }
        return ret;
    };

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public SeriesDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("series")
                .usingGeneratedKeyColumns("id");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS series(" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(32)," +
                "description VARCHAR(256)," +
                "userRating FLOAT" +
                ")");
    }

    @Override
    public List<Series> getSeriesByName(String seriesName) {
        return jdbcTemplate.query("SELECT * FROM series WHERE name = ?", new Object[]{seriesName}, seriesRowMapper);
    }

    @Override
    public Series getSeriesById(final long id) {
        final List<Series> seriesList = jdbcTemplate.query("SELECT * FROM series WHERE id = ?", new Object[]{id}, seriesRowMapper);
        if(seriesList.isEmpty()) {
            return null;
        }
        return seriesList.get(0);
    }

    @Override
    public long createSeries(String seriesName, String seriesDescription) {
        Map<String, Object> args = new HashMap<>();
        args.put("name", seriesName);
        args.put("description", seriesDescription);

        final Number seriesId = jdbcInsert.executeAndReturnKey(args);
        return seriesId.longValue();
    }



}
