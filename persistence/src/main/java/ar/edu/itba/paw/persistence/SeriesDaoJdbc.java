package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.*;
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
    private SimpleJdbcInsert followsjdbcInsert;
    private SimpleJdbcInsert viewedEpisodesjdbcInsert;

    @Autowired
    private UserDao userDao;

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
        followsjdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("follows");
        viewedEpisodesjdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("hasviewedepisode");
    }

    @Override
    public List<Series> searchSeries(String seriesName, String genreName, String networkName,int minRating,int maxRating) {
        //TODO agregar "userRating BETWEEN minRating AND maxRating" a la query cuando los puntajes en la base no esten en null.
        return groupGenres(jdbcTemplate.query("SELECT * " +
                "FROM (series LEFT JOIN hasgenre ON series.id = hasgenre.seriesid LEFT JOIN genres ON genres.id = hasgenre.genreid LEFT JOIN network ON network.networkid = series.networkid) " +
                "AS foo(id,tvdbid, name, description, userRating, status, runtime, networkid, firstaired, id_imdb, added, updated, posterurl, followers, bannerurl, seriesid, genreid, genreid1, genre, networkid1, networkname) " +
                "WHERE LOWER(name) LIKE ? AND LOWER(genre) LIKE ? AND LOWER(networkname) LIKE ?",
                new Object[]{"%"+seriesName.toLowerCase()+"%","%"+genreName.toLowerCase()+"%","%"+networkName.toLowerCase()+"%"}, seriesRowMapper));
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
                "AS foo(id, tvdbid,name, description, userRating, status, runtime, networkid, firstaired, id_imdb, added, updated, posterurl, followers, bannerurl, seriesid, genreid, genreid1, genre, networkid1, networkname) " +
                "WHERE bannerurl IS NOT NULL " +
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
    @Override
    public List<Genre> getAllGenres() {
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
    public Series getSeriesById(final long id, final long userId) {
        final List<Series> seriesList = jdbcTemplate.query("SELECT * " +
                "FROM (series LEFT JOIN hasGenre ON hasgenre.seriesid = series.id LEFT JOIN genres ON hasgenre.genreid = genres.id LEFT JOIN network ON network.networkid = series.networkid) " +
                "AS foo(id, tvdbid,name, description, userRating, status, runtime, networkid, firstaired, id_imdb, added, updated, posterurl, followers, bannerurl, seriesid, genreid, genreid1, genre, networkid1, networkname)" +
                "WHERE id = ?", new Object[]{id}, seriesRowMapper);
        if(seriesList.isEmpty()) {
            return null;
        }
        Series series = groupGenres(seriesList).get(0);
        addAllSeasonsToSeries(series, userId);
        addAllPostsToSeries(series);
        series.setFollows(userFollows(id, userId));
        return series;
    }

    private void addAllSeasonsToSeries(Series s, long userId) {
        List<Season> seasonList = getSeasonsBySeriesId(s.getId());
        for(Season season : seasonList) {
            season.setEpisodes(getEpisodesBySeasonId(season.getId(), userId));
        }
        setSeasonsViewed(seasonList);
        s.setSeasons(seasonList);
    }

    private void setSeasonsViewed(List<Season> seasonList) {
        for(Season season : seasonList) {
            season.setViewed(season.getEpisodeList().stream().allMatch(episode -> episode.isViewed()));
        }
    }

    private void addAllPostsToSeries(Series series) {
        final List<Post> postList = jdbcTemplate.query("SELECT seriesreview.id, seriesreview.body, seriesreview.numlikes, seriesreview.userid " +
                "FROM seriesreview JOIN series ON seriesreview.seriesid = series.id " +
                        "WHERE series.id = ?", new Object[]{series.getId()},
                (resultSet, i) -> {
                    Post post = new Post();
                    post.setBody(resultSet.getString("body"));
                    post.setPoints(resultSet.getInt("numlikes"));
                    post.setPostId(resultSet.getInt("id"));
                    post.setUserId(resultSet.getLong("userid"));
                    return post;
                });
        addUserToPosts(postList);
        addAllCommentsToPosts(postList);
        series.setSeriesPostList(postList);
    }

    private void addAllCommentsToPosts(List<Post> postList) {
        for(Post post : postList) {
            addAllCommentsToPost(post);
        }
    }

    private void addAllCommentsToPost(Post post) {
        List<Comment> commentsList = jdbcTemplate.query("SELECT * " +
                "FROM seriesreviewcomments " +
                "WHERE postid = ?", new Object[]{post.getPostId()},
                (resultSet, i) -> {
                    Comment comment = new Comment();
                    comment.setCommentId(resultSet.getInt("id"));
                    comment.setBody(resultSet.getString("body"));
                    comment.setPoints(resultSet.getInt("numlikes"));
                    comment.setUserId(resultSet.getLong("userid"));
                    return comment;
                });
        addUsersToComments(commentsList);
        post.setComments(commentsList);
    }

    private void addUsersToComments(List<Comment> commentsList) {
        for(Comment comment : commentsList) {
            comment.setUser(userDao.getUserById(comment.getUserId()));
        }
    }

    private void addUserToPosts(List<Post> postList) {
        for(Post post : postList) {
            post.setUser(userDao.getUserById(post.getUserId()));
        }
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

    @Override
    public List<Season> getSeasonsBySeriesId(long seriesId) {
        List<Season> seasonList = jdbcTemplate.query("SELECT * " +
                "FROM series JOIN season ON series.id = season.seriesId " +
                "WHERE series.id = ? " +
                "ORDER BY seasonnumber", new Object[]{seriesId}, (resultSet, i) -> {
           Season s = new Season();
           s.setSeasonNumber(resultSet.getInt("seasonnumber"));
           s.setId(resultSet.getLong("seasonid"));
           return s;
        });
        return seasonList;
    }

    @Override
    public List<Episode> getEpisodesBySeasonId(long seasonId, long userId) {
        List<Episode> episodeList = jdbcTemplate.query("SELECT episode.*, exists(SELECT * FROM hasviewedepisode WHERE userid = ? AND hasviewedepisode.episodeid = episode.id) as viewed " +
                "FROM episode  " +
                "WHERE episode.seasonid = ? " +
                "ORDER BY numepisode", new Object[]{userId, seasonId}, (resultSet, i) -> {
            Episode ret = new Episode();
            ret.setEpisodeNumber(resultSet.getInt("numepisode"));
            ret.setDescription(resultSet.getString("overview"));
            ret.setName(resultSet.getString("name"));
            ret.setViewed(resultSet.getBoolean("viewed"));
            ret.setId(resultSet.getLong("id"));
            return ret;
        });
        return episodeList;
    }

    @Override
    public List<Comment> getSeriesCommentsById(long seriesId) {
        return jdbcTemplate.query("SELECT * " +
                "FROM seriesreview " +
                "WHERE seriesId = ?",
                new Object[]{seriesId},(resultSet,i) -> {
                   Comment comment = new Comment();
                   comment.setCommentId(resultSet.getLong("id"));
                   comment.setUserId(resultSet.getLong("userId"));
                   comment.setBody(resultSet.getString("body"));
                   comment.setPoints(resultSet.getInt("numLikes"));
                   comment.setUser(userDao.getUserById(comment.getUserId()));
                   return comment;
                });
    }

    @Override
    public List<Series> getNextToBeSeen(long userId) {
        List<Series> seriesList = jdbcTemplate.query("SELECT episode.id AS episodeid,\n" +
                        "episode.name AS episodename,\n" +
                        "episode.numepisode AS episodenumber,\n" +
                        "(SELECT series.id FROM series WHERE series.id = episode.seriesid) AS seriesid,\n" +
                        "(SELECT series.name FROM series WHERE series.id = episode.seriesid) AS seriesname,\n" +
                        "(SELECT series.bannerurl FROM series WHERE series.id = episode.seriesid) AS seriesbannerurl,\n" +
                        "(SELECT season.seasonid FROM season WHERE season.seasonid = episode.seasonid) AS seasonid,\n" +
                        "(SELECT season.seasonnumber FROM season WHERE season.seasonid = episode.seasonid) AS seasonnumber\n" +
                        "FROM episode\n" +
                        "WHERE NOT EXISTS(SELECT * FROM hasviewedepisode WHERE hasviewedepisode.episodeid = episode.id AND hasviewedepisode.userid = 1)\n" +
                        "AND NOT EXISTS(SELECT e1.id FROM episode AS e1 WHERE NOT EXISTS(SELECT hasviewedepisode.episodeid\n" +
                        "FROM hasviewedepisode\n" +
                        "WHERE hasviewedepisode.episodeid = e1.id AND hasviewedepisode.userid = 1) AND e1.seriesid = episode.seriesid AND e1.aired < episode.aired)\n" +
                        "AND episode.seriesid IN (SELECT follows.seriesid FROM follows WHERE follows.userid = 1)",
                new Object[]{userId, userId}, (resultSet, i) -> {
                    Series series = new Series();
                    series.setName(resultSet.getString("seriesname"));
                    series.setId(resultSet.getLong("seriesid"));
                    series.setBannerUrl(resultSet.getString("seriesbannerurl"));

                    List<Season> auxSeasonList = new ArrayList<>(1);
                    Season season = new Season();
                    season.setId(resultSet.getLong("seasonid"));
                    season.setSeasonNumber(resultSet.getInt("seasonnumber"));

                    List<Episode> auxEpisodeList = new ArrayList<>(1);
                    Episode episode = new Episode();
                    episode.setId(resultSet.getLong("episodeid"));
                    episode.setName(resultSet.getString("episodename"));
                    episode.setEpisodeNumber(resultSet.getInt("episodenumber"));

                    auxEpisodeList.add(episode);
                    season.setEpisodeList(auxEpisodeList);
                    series.setSeasons(auxSeasonList);

                    return series;
                });
        //Todo: process list
        return seriesList;
    }

    @Override
    public void followSeries(long seriesId, long userId) {

        //Todo: ask if there is a utility method for this.
        if(userFollows(seriesId, userId)) return;

        Map<String, Object> args = new HashMap<>();
        args.put("userId",userId);
        args.put("seriesId", seriesId);

        followsjdbcInsert.execute(args);
        jdbcTemplate.update("UPDATE series SET followers = (followers + 1) WHERE id = ?", seriesId);
    }

    private boolean userFollows(long seriesId, long userId) {
        List<Boolean> series = jdbcTemplate.query("SELECT exists(SELECT * " +
                "FROM follows WHERE follows.userid = ? AND follows.seriesid = ?) AS viewed", new Object[]{userId, seriesId},
                (resultSet, i) -> resultSet.getBoolean("viewed"));
        return series.get(0);
    }

    @Override
    public void setViewedEpisode(long episodeId, long userId) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId",userId);
        args.put("episodeId", episodeId);

        viewedEpisodesjdbcInsert.execute(args);
    }

}
