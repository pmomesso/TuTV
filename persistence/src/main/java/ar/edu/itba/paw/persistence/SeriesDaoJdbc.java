package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class SeriesDaoJdbc implements SeriesDao {

    private RowMapper<Series> seriesRowMapper = (resultSet, i) -> {
        //Creo la serie.
        Series ret = new Series(resultSet.getString("name"));
        String description = resultSet.getString("description");
        if (description != null) {
            ret.setDescription(description);
        }
        ret.setId(resultSet.getLong("id"));
        ret.setUserRating(resultSet.getDouble("userRating"));
        ret.setImdbId(resultSet.getString("id_imdb"));
        ret.setNetworkId(resultSet.getInt("networkId"));
        ret.setRunningTime(resultSet.getInt("runtime"));
        ret.setNumFollowers(resultSet.getInt("followers"));
        ret.setBannerUrl(resultSet.getString("bannerurl"));
        ret.setPosterUrl(resultSet.getString("posterurl"));
        ret.setStatus(resultSet.getString("status"));
        ret.setNetwork(resultSet.getString("networkname"));
        //Seteo las fechas.
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String added = resultSet.getString("added");
        if(added != null){
            try {
                ret.setAdded(format.parse(added));
            } catch (ParseException e) {e.printStackTrace();}
        }
        String updated = resultSet.getString("updated");
        if(updated != null){
            try {
                ret.setUpdated(format.parse(updated));
            } catch (ParseException e) {e.printStackTrace();}
        }
        String firstAired = resultSet.getString("firstAired");
        if(firstAired != null){
            try {
                ret.setFirstAired(format.parse(firstAired));
            } catch (ParseException e) {e.printStackTrace();}
        }
        //Creo el genero.
        if(resultSet.getString("genre") != null){
            Genre g = new Genre();
            g.setName(resultSet.getString("genre"));
            g.setId(resultSet.getInt("genreid"));
            ret.addGenre(g);
        }
        return ret;
    };

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert seriesjdbcInsert;
    private SimpleJdbcInsert genresjdbcInsert;
    private SimpleJdbcInsert hasGenrejdbcInsert;

    private Series getById(List<Series> series,long id){
        for(Series s : series){
            if(s.getId() == id)
                return s;
        }
        return null;
    }

    private List<Series> groupGenres(List<Series> seriesList){
        List<Series> retList = new ArrayList<>();
        Series s;
        for(Series series : seriesList){
            s = getById(retList,series.getId());
            if(s != null){
                s.addGenres(series.getGenres());
            }
            else{
                retList.add(series);
            }
        }
        return retList;
    }
    @Autowired
    public SeriesDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        seriesjdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("series")
                .usingGeneratedKeyColumns("id");
        genresjdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("genres")
                .usingGeneratedKeyColumns("id");
        hasGenrejdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("hasgenre");
    }

    @Override
    public List<Series> getSeriesByName(String seriesName) {
        return groupGenres(jdbcTemplate.query("SELECT * " +
                "FROM (series LEFT JOIN hasgenre ON series.id = hasgenre.seriesid LEFT JOIN genres ON genres.id = hasgenre.genreid LEFT JOIN network ON network.networkid = series.networkid) " +
                "AS foo(id,tvdbid, name, description, userRating, status, runtime, networkid, firstaired, id_imdb, added, updated, posterurl, followers, bannerurl, seriesid, genreid, genreid1, genre, networkid1, networkname) " +
                "WHERE LOWER(name) LIKE ?",new Object[]{"%"+seriesName.toLowerCase()+"%"}, seriesRowMapper));
    }

    @Override
    public List<Series> getSeriesByGenre(String genreName) {
        return groupGenres(jdbcTemplate.query("SELECT * " +
                "FROM (series JOIN hasGenre ON hasgenre.seriesid = series.id JOIN genres ON hasgenre.genreid = genres.id LEFT JOIN network ON network.networkid = series.networkid) " +
                "AS foo(id, tvdbid,name, description, userRating, status, runtime, networkid, firstaired, id_imdb, added, updated, posterurl, followers, bannerurl, seriesid, genreid, genreid1, genre, networkid1, networkname)" +
                "WHERE LOWER(genre) LIKE ?", new Object[]{"%"+genreName.toLowerCase()+"%"}, seriesRowMapper));
    }

    @Override
    public List<Series> getSeriesByGenre(int id) {
        return groupGenres(jdbcTemplate.query("SELECT * " +
                "FROM (series JOIN hasGenre ON hasgenre.seriesid = series.id JOIN genres ON hasgenre.genreid = genres.id LEFT JOIN network ON network.networkid = series.networkid) " +
                "AS foo(id, tvdbid,name, description, userRating, status, runtime, networkid, firstaired, id_imdb, added, updated, posterurl, followers, bannerurl, seriesid, genreid, genreid1, genre, networkid1, networkname)" +
                "WHERE genreid = ?", new Object[]{id}, seriesRowMapper));
    }

    @Override
    public List<Series> getBestSeriesByGenre(int genreId, int lowerLimit, int upperLimit) {
        return groupGenres(jdbcTemplate.query("SELECT * " +
                        "FROM (series JOIN hasGenre ON hasgenre.seriesid = series.id JOIN genres ON hasgenre.genreid = genres.id LEFT JOIN network ON network.networkid = series.networkid) " +
                        "AS foo(id, tvdbid,name, description, userRating, status, runtime, networkid, firstaired, id_imdb, added, updated, posterurl, followers, bannerurl, seriesid, genreid, genreid1, genre, networkid1, networkname)" +
                        "WHERE genreid = ?" +
                        "ORDER BY userRating DESC LIMIT ? OFFSET ?",
                new Object[]{genreId, upperLimit - lowerLimit + 1, lowerLimit}, seriesRowMapper));
    }

    @Override
    public List<Series> getNewSeries(int lowerLimit, int upperLimit) {
        return groupGenres(jdbcTemplate.query("SELECT * " +
                "FROM (series LEFT JOIN hasGenre ON hasgenre.seriesid = series.id LEFT JOIN genres ON hasgenre.genreid = genres.id LEFT JOIN network ON network.networkid = series.networkid) " +
                "AS foo(id, tvdbid,name, description, userRating, status, runtime, networkid, firstaired, id_imdb, added, updated, posterurl, followers, bannerurl, seriesid, genreid, genreid1, genre, networkid1, networkname)" +
                "ORDER BY firstaired DESC LIMIT ? OFFSET ?", new Object[]{upperLimit - lowerLimit + 1, lowerLimit}, seriesRowMapper));
    }

    @Override
    public Map<Genre, List<Series>> getBestSeriesByGenres(int lowerLimit, int upperLimit) {
        Map<Genre, List<Series>> ret = new HashMap<>();

        List<Genre> genreList = getAllGenres();
        List<Series> seriesList;
        for(Genre g : genreList) {
            seriesList = getBestSeriesByGenre(g.getId(), lowerLimit, upperLimit);
            ret.put(g, seriesList);
        }

        return ret;
    }

    private List<Genre> getAllGenres() {
        return jdbcTemplate.query("SELECT * " +
                                "FROM genres",
                          (resultSet, i) -> {
                                Genre g = new Genre();
                                g.setName(resultSet.getString("genre"));
                                g.setId(resultSet.getInt("id"));
                                return g;
                          });
    }

    @Override
    public Series getSeriesById(final long id) {
        final List<Series> seriesList = jdbcTemplate.query("SELECT * " +
                "FROM (series LEFT JOIN hasGenre ON hasgenre.seriesid = series.id LEFT JOIN genres ON hasgenre.genreid = genres.id LEFT JOIN network ON network.networkid = series.networkid) " +
                "AS foo(id, tvdbid,name, description, userRating, status, runtime, networkid, firstaired, id_imdb, added, updated, posterurl, followers, bannerurl, seriesid, genreid, genreid1, genre, networkid1, networkname)" +
                "WHERE id = ?", new Object[]{id}, seriesRowMapper);
        if(seriesList.isEmpty()) {
            return null;
        }
        return groupGenres(seriesList).get(0);
    }

    @Override
    public long createSeries(Integer tvdbid,String seriesName, String seriesDescription,Double userRating,String status,
                             Integer runtime,Integer networkId,String firstAired,String idImdb,String added,String updated,
                             String posterUrl,String bannerUrl,Integer followers) {

        seriesjdbcInsert = seriesjdbcInsert.withTableName("series")
                                .usingGeneratedKeyColumns("id");

        Map<String, Object> args = new HashMap<>();
        args.put("tvdbid",tvdbid);
        args.put("name", seriesName);
        args.put("description", seriesDescription);
        args.put("userRating",userRating);
        args.put("status",status);
        args.put("runtime",runtime);
        args.put("networkId",networkId);
        args.put("firstAired",firstAired);
        args.put("id_imdb",idImdb);
        args.put("added",added);
        args.put("updated",updated);
        args.put("posterUrl",posterUrl);
        args.put("bannerUrl",bannerUrl);
        args.put("followers",followers);

        final Number seriesId = seriesjdbcInsert.executeAndReturnKey(args);
        return seriesId.longValue();
    }

    @Override
    public long addSeriesGenre(String genreName,List<Series> series) {

        genresjdbcInsert = genresjdbcInsert.withTableName("genres")
                                .usingGeneratedKeyColumns("id");
        Map<String, Object> args = new HashMap<>();
        args.put("genre", genreName);
        final Number genreId = genresjdbcInsert.executeAndReturnKey(args);
        if(series != null && series.size() > 0){
            hasGenrejdbcInsert = hasGenrejdbcInsert.withTableName("hasgenre");
            args.clear();
            MapSqlParameterSource entry;
            List<MapSqlParameterSource> entries = new ArrayList<>();
            for(Series s : series){
                entry = new MapSqlParameterSource()
                        .addValue("seriesid",s.getId())
                        .addValue("genreid",genreId);
                entries.add(entry);
            }
            hasGenrejdbcInsert.executeBatch(entries.toArray(new MapSqlParameterSource[entries.size()]));
        }
        return genreId.longValue();
    }

    @Override
    public void setSeriesRunningTime(long seriesId, int runningTime) {
        jdbcTemplate.update("UPDATE series SET runtime = ? WHERE id = ?", runningTime, seriesId);
    }

    @Override
    public void setSeriesNetwork(long seriesId, int networkId) {
        jdbcTemplate.update("UPDATE series SET networkId = ? WHERE id = ?", networkId, seriesId);
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
