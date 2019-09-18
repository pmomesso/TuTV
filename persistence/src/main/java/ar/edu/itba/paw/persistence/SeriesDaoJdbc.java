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
        Series ret = new Series(resultSet.getString("name"));
        String description = resultSet.getString("description");
        if (description != null) {
            ret.setDescription(description);
        }
        double userRating = resultSet.getDouble("userRating");
        if(Double.compare(userRating, 0) != 0) {
            ret.setUserRating(userRating);
        }
        String network = resultSet.getString("network");
        ret.setNetwork(network);
        int runningTime = resultSet.getInt("runningTime");
        ret.setRunningTime(runningTime);
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

        jdbcInsert = jdbcInsert.withTableName("series")
                                .usingGeneratedKeyColumns("id");

        Map<String, Object> args = new HashMap<>();
        args.put("name", seriesName);
        args.put("description", seriesDescription);

        final Number seriesId = jdbcInsert.executeAndReturnKey(args);
        return seriesId.longValue();
    }

    @Override
    public void addSeriesGenre(long seriesId, String genre) {

        jdbcInsert = jdbcInsert.withTableName("genres");

        Map<String, Object> args = new HashMap<>();
        args.put("id", seriesId);
        args.put("genre", genre);

        jdbcInsert.execute(args);
    }

    @Override
    public void setSeriesRunningTime(long seriesId, int runningTime) {
        jdbcTemplate.update("UPDATE series SET runningTime = ? WHERE id = ?", runningTime, seriesId);
    }

    @Override
    public void setSeriesNetwork(long seriesId, String network) {
        jdbcTemplate.update("UPDATE series SET network = ? WHERE id = ?", network, seriesId);
    }

    @Override
    public void setSeriesDescription(long seriesId, String description) {
        jdbcTemplate.update("UPDATE series SET description = ? WHERE id = ?", description, seriesId);
    }

}
