package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.model.Genre;
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
        String network = resultSet.getString("network");
        ret.setNetwork(network);
        int runningTime = resultSet.getInt("runtime");
        ret.setRunningTime(runningTime);
        Genre genre = new Genre();
        genre.setName(resultSet.getString("genre"));
        ret.addGenre(genre);
        ret.setId(resultSet.getLong("id"));
        ret.setNumFollowers(resultSet.getInt("followers"));
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
        return jdbcTemplate.query("SELECT * " +
                "FROM series " +
                "WHERE name = ?", new Object[]{seriesName}, seriesRowMapper);
    }

    @Override
    public List<Series> getSeriesByGenre(Genre genre) {
        return jdbcTemplate.query("SELECT * " +
                "FROM series JOIN genres ON series.genreId = genre.id " +
                "WHERE genres.genre = ? " +
                "ORDER BY userRating DESC",
                new Object[]{genre.toString()}, seriesRowMapper);
    }

    @Override
    public List<Series> getBestSeriesByGenre(Genre genre, int lowerLimit, int upperLimit) {
        return jdbcTemplate.query("SELECT * " +
                        "FROM series JOIN genres ON series.genreId = genre.id " +
                        "WHERE genres.genre = ? " +
                        "ORDER BY userRating DESC LIMIT ? OFFSET ?",
                new Object[]{genre.toString(), upperLimit - lowerLimit + 1, lowerLimit}, seriesRowMapper);
    }

    @Override
    public List<Series> getNewSeries(int lowerLimit, int upperLimit) {
        return jdbcTemplate.query("SELECT * " +
                "FROM series " +
                "ORDER BY firstaired DESC LIMIT ? OFFSET ?", new Object[]{upperLimit - lowerLimit + 1, upperLimit}, seriesRowMapper);
    }

    @Override
    public Map<Genre, List<Series>> getBestSeriesByGenres(int lowerLimit, int upperLimit) {
        Map<Genre, List<Series>> ret = new HashMap<>();

        List<Genre> genreList = getAllGenres();
        List<Series> seriesList;
        for(Genre g : genreList) {
            seriesList = getBestSeriesByGenre(g, lowerLimit, upperLimit);
            ret.put(g, seriesList);
        }

        return ret;
    }

    private List<Genre> getAllGenres() {
        return jdbcTemplate.query("SELECT DISTINCT genre " +
                                "FROM genres",
                          (resultSet, i) -> {
                                Genre g = new Genre();
                                g.setName(resultSet.getString("genres"));
                                return g;
                          });
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
        long genreId = getGenreId(genre);
        Map<String, Object> args = new HashMap<>();
        args.put("id", seriesId);
        args.put("genreId", genreId);

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


    private long getGenreId(String genre) {
        List<Long> genreId = jdbcTemplate.query("SELECT id" +
                        "FROM genres" +
                        "WHERE genre = ?", new Object[]{genre},
                ((resultSet, i) -> resultSet.getLong("id")));
        return genreId.get(0);
    }

}
