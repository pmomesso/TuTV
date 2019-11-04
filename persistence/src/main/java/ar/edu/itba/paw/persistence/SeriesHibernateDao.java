package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class SeriesHibernateDao implements SeriesDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Series> searchSeries(String seriesName, String genreName, String networkName) {
        //Valores por defecto
        seriesName = seriesName != null ? seriesName : "";
        genreName = genreName != null ? genreName : "";
        networkName = networkName != null ? networkName : "";

        final TypedQuery<Series> query = em.createQuery("select distinct s from Series as s inner join s.genres as g " +
                "where lower(s.name) like :seriesName and lower(s.network.name) like :networkName and lower(g.name) like :genreName",Series.class);
        query.setParameter("seriesName","%" + seriesName.toLowerCase() + "%");
        query.setParameter("networkName","%" + networkName.toLowerCase() + "%");
        query.setParameter("genreName","%" + genreName.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public List<Series> getSeriesByName(String seriesName) {
        final TypedQuery<Series> query = em.createQuery("select s from Series as s inner join s.genres as g " +
                "where s.name like :seriesName",Series.class);
        query.setParameter("seriesName",seriesName);
        return query.getResultList();
    }

    @Override
    public List<Series> getSeriesByGenre(String genreName) {
        final TypedQuery<Series> query = em.createQuery("select s from Series as s inner join s.genres as g " +
                "where g.name like :genreName",Series.class);
        query.setParameter("genreName",genreName);
        return query.getResultList();
    }

    @Override
    public List<Series> getSeriesByGenre(long id) {
        final TypedQuery<Series> query = em.createQuery("select s from Series as s inner join s.genres as g " +
                "where g.id = :genreId",Series.class);
        query.setParameter("genreId",id);
        return query.getResultList();
    }

    @Override
    public List<Series> getBestSeriesByGenre(int genreId, int lowerLimit, int upperLimit) {
        final TypedQuery<Series> query = em.createQuery("from Series as s inner join s.genres as g " +
                "where g.id = :genreId and s.userRating between :lowerLimit and :upperLimit",Series.class);
        query.setParameter("genreId",genreId);
        return query.getResultList();
    }

    @Override
    public List<Genre> getAllGenres() {
        final TypedQuery<Genre> query = em.createQuery("from Genre order by name", Genre.class);
        return query.getResultList();
    }

    @Override
    public List<Network> getAllNetworks() {
        final TypedQuery<Network> query = em.createQuery("from Network order by name", Network.class);
        return query.getResultList();
    }

    @Override
    public List<Series> getNewSeries(int lowerLimit, int upperLimit) {
        final TypedQuery<Series> query = em.createQuery("select s from Series as s inner join s.seasons as season inner join season.episodes as e " +
                "order by e.aired desc",Series.class);
        Object[] distinctSeries = new HashSet<>(query.getResultList()).toArray();
        List<Series> result = new ArrayList<>();
        for(int i = lowerLimit; i < upperLimit ; i++){
            result.add((Series)distinctSeries[i]);
        }
        return result;
    }

    private List<Series> getBestSeriesByGenre(long genreId,int limit,int offset){
        return em.createNativeQuery("SELECT * " +
                "FROM (series JOIN hasGenre ON hasgenre.seriesid = series.id JOIN genres ON hasgenre.genreid = genres.id LEFT JOIN network ON network.id = series.network_id) " +
                "WHERE genreid = ?" +
                "ORDER BY userRating DESC LIMIT ? OFFSET ?",Series.class)
                .setParameter(1,genreId)
                .setParameter(2,limit)
                .setParameter(3,offset)
                .getResultList();
    }
    @Override
    public Map<Genre,List<Series>> getBestSeriesByGenres(int lowerLimit, int upperLimit) {
        Map<Genre,List<Series>> genreMap = new TreeMap<>(Comparator.comparing(Genre::getName));
        for(Genre g : getAllGenres()){
            genreMap.put(g,getBestSeriesByGenre(g.getId(),upperLimit - lowerLimit + 1,lowerLimit));
        }
        return genreMap;
    }

    @Override
    public Optional<Series> getSeriesById(long id) {
        return Optional.ofNullable(em.find(Series.class,id));
    }

    @Override
    public Optional<Series> createSeries(Integer tvdbid, String seriesName, String seriesDescription, Double userRating, String status, Integer runtime, long networkId, String firstAired, String idImdb, String added, String updated, String posterUrl, String bannerUrl, Integer followers) {
        Optional<Network> network = Optional.ofNullable(em.find(Network.class,networkId));
        if(!network.isPresent()){
            return Optional.empty();
        }
        Series series =  new Series(tvdbid, seriesName,seriesDescription, network.get(),posterUrl,bannerUrl,userRating,status,runtime,followers,idImdb,firstAired,added,updated);
        em.persist(series);
        return Optional.of(series);
    }

    @Override
    public long addSeriesGenre(String genreName, List<Series> series) {
       TypedQuery<Genre> query = em.createQuery("from Genre where name = :genreName", Genre.class);
       query.setParameter("genreName",genreName);
       List<Genre> genreList = query.getResultList();
       if(genreList.size() == 0){
           return -1;
       }
       Genre genre = genreList.get(0);
       Optional<Series> current;
       for(Series s : series){
           current = getSeriesById(s.getId());
           current.ifPresent(value -> value.addGenre(genre));
       }
       return genre.getId();
    }

    @Override
    public List<Season> getSeasonsBySeriesId(long seriesId) {
        TypedQuery<Season> query = em.createQuery("from Season where series.id = :seriesId",Season.class);
        query.setParameter("seriesId",seriesId);
        return query.getResultList();
    }

    @Override
    public List<Episode> getEpisodesBySeasonId(long seasonId) {
        TypedQuery<Episode> query = em.createQuery("from Episode where season.id = :seasonId",Episode.class);
        query.setParameter("seasonId",seasonId);
        return query.getResultList();
    }

    @Override
    public List<Episode> getNextToBeSeen(long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        List<Episode> nextToBeSeen = new ArrayList<>();
        if(user.isPresent()){
            List<Season> seasons = new ArrayList<>();
            List<Episode> episodes = new ArrayList<>();
            boolean done;
            //TODO: ver si es posible optimizar
            for(Series series : user.get().getFollows()){
                done = false;
                seasons.addAll(series.getSeasons());
                seasons.sort(Comparator.comparingInt(Season::getSeasonNumber));
                for(Season season : seasons){
                    episodes.addAll(season.getEpisodes());
                    episodes.sort(Comparator.comparingInt(Episode::getNumEpisode));
                    for(Episode episode : episodes){
                        if(!user.get().getViewed().contains(episode)){
                            nextToBeSeen.add(episode);
                            done = true;
                            break;
                        }
                    }
                    episodes.clear();
                    if(done){break;}
                }
                seasons.clear();
            }
        }
        return nextToBeSeen;
    }

    @Override
    public Optional<List<Series>> getRecentlyWatched(long userId, int number) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Set<Series> recentlyWatched = new HashSet<>();
        int i = 0;
        if(user.isPresent()){
            for(Episode e: user.get().getViewed()){
                recentlyWatched.add(e.getSeason().getSeries());
                if(i++ >= number){break;}
            }
        }

        return Optional.of(new ArrayList<>(recentlyWatched));
    }

    @Override
    public Optional<List<Series>> getAddedSeries(long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        List<Series> followed = new ArrayList<>();
        user.ifPresent(value -> followed.addAll(value.getFollows()));
        return Optional.of(followed);
    }

    @Override
    public Optional<List<Episode>> getUpcomingEpisodes(long userId) {
        TypedQuery<Episode> query = em.createQuery("from Episode as e where e.aired >= :today",Episode.class);
        query.setParameter("today",new Date(), TemporalType.DATE);
        return Optional.of(query.getResultList());
    }

    @Override
    public boolean userFollows(long seriesId, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        boolean result = false;
        if(user.isPresent()){
            for(Series s : user.get().getFollows()){
                if(s.getId() == seriesId){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public int followSeries(long seriesId, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<Series> series = Optional.ofNullable(em.find(Series.class,seriesId));
        int updated = 0;
        if(user.isPresent() && series.isPresent()){
            series.get().addUserFollower(user.get());
            series.get().setFollowers(series.get().getFollowers() + 1);
            updated++;
        }
        return updated;
    }

    @Override
    public int unfollowSeries(long seriesId, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<Series> series = Optional.ofNullable(em.find(Series.class,seriesId));
        int updated = 0;
        if(user.isPresent() && series.isPresent()){
            series.get().removeUserFollower(user.get());
            series.get().setFollowers(series.get().getFollowers() - 1);
            updated++;
        }
        return updated;
    }

    @Override
    public int setViewedEpisode(long episodeId, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<Episode> episode = Optional.ofNullable(em.find(Episode.class,episodeId));
        int updated = 0;
        if(user.isPresent() && episode.isPresent()){
            episode.get().addViewer(user.get());
            updated++;
        }
        return updated;
    }

    @Override
    public int setViewedSeason(long seasonId, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<Season> season = Optional.ofNullable(em.find(Season.class,seasonId));
        int updated = 0;
        if(user.isPresent() && season.isPresent()){
            for(Episode episode : season.get().getEpisodes()){
                episode.addViewer(user.get());
                updated++;
            }
        }
        return updated;
    }

    @Override
    public int unviewSeason(long seasonId, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<Season> season = Optional.ofNullable(em.find(Season.class,seasonId));
        int updated = 0;
        if(user.isPresent() && season.isPresent()){
            for(Episode episode : season.get().getEpisodes()){
                episode.removeViewer(user.get());
                updated++;
            }
        }
        return updated;
    }

    @Override
    public int unviewEpisode(long userId, long episodeId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<Episode> episode = Optional.ofNullable(em.find(Episode.class,episodeId));
        int updated = 0;
        if(user.isPresent() && episode.isPresent()){
            episode.get().removeViewer(user.get());
            updated++;
        }
        return updated;
    }

    @Override
    public Optional<SeriesReview> createSeriesReview(String body, long seriesId, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<Series> series = Optional.ofNullable(em.find(Series.class,seriesId));
        if(!user.isPresent() || !series.isPresent()){
            return Optional.empty();
        }
        SeriesReview review = new SeriesReview(body,series.get(),user.get());
        em.persist(review);
        user.get().getSeriesReviews().add(review);
        series.get().getSeriesReviewList().add(review);
        return Optional.of(review);
    }

    @Override
    public int likePost(long userId, long postId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<SeriesReview> review = Optional.ofNullable(em.find(SeriesReview.class,postId));
        int updated = 0;
        if(user.isPresent() && review.isPresent()){
            review.get().addLike(user.get());
            review.get().setNumLikes(review.get().getNumLikes() + 1);
            updated++;
        }
        return updated;
    }

    @Override
    public int unlikePost(long userId, long postId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<SeriesReview> review = Optional.ofNullable(em.find(SeriesReview.class,postId));
        int updated = 0;
        if(user.isPresent() && review.isPresent()){
            review.get().removeLike(user.get());
            review.get().setNumLikes(review.get().getNumLikes() - 1);
            updated++;
        }
        return updated;
    }

    @Override
    public Optional<SeriesReviewComment> addCommentToPost(long commentPostId, String commentBody, long commentUserId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,commentUserId));
        Optional<SeriesReview> review = Optional.ofNullable(em.find(SeriesReview.class,commentPostId));
        int updated = 0;
        if(user.isPresent() && review.isPresent()){
            SeriesReviewComment comment = new SeriesReviewComment(commentBody,review.get(),user.get());
            em.persist(comment);
            user.get().getSeriesReviewComments().add(comment);
            review.get().getComments().add(comment);
            return Optional.of(comment);
        }
        else{
            return Optional.empty();
        }
    }

    @Override
    public int likeComment(long userId, long commentId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<SeriesReviewComment> comment = Optional.ofNullable(em.find(SeriesReviewComment.class,commentId));
        int updated = 0;
        if(user.isPresent() && comment.isPresent()){
            comment.get().addLike(user.get());
            comment.get().setNumLikes(comment.get().getNumLikes() + 1);
            updated++;
        }
        return updated;
    }

    @Override
    public int unlikeComment(long userId, long commentId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<SeriesReviewComment> comment = Optional.ofNullable(em.find(SeriesReviewComment.class,commentId));
        int updated = 0;
        if(user.isPresent() && comment.isPresent()){
            comment.get().removeLike(user.get());
            comment.get().setNumLikes(comment.get().getNumLikes() - 1);
            updated++;
        }
        return updated;
    }

    @Override
    public int removeComment(long commentId) {
        Optional<SeriesReviewComment> comment = Optional.ofNullable(em.find(SeriesReviewComment.class,commentId));
        int updated = 0;
        if(comment.isPresent()){
            em.remove(comment.get());
            updated++;
        }
        return updated;
    }

    @Override
    public int removePost(long postId) {
        Optional<SeriesReview> review = Optional.ofNullable(em.find(SeriesReview.class,postId));
        int updated = 0;
        if(review.isPresent()){
            em.remove(review.get());
            updated++;
        }
        return updated;
    }

    @Override
    public int rateSeries(long seriesId, long userId, int rating) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        boolean done = false;
        if(user.isPresent()){
            Optional<Series> series = Optional.ofNullable(em.find(Series.class,seriesId));
            if(series.isPresent()){
                Rating r = null;
                for(Rating userRate : user.get().getRatings()){
                    if(userRate.getSeries().getId() == series.get().getId()){
                        r = userRate;
                        r.setRating(rating);
                        break;
                    }
                }
                if(r == null){
                    r = new Rating(user.get(),series.get(),rating);
                    em.persist(r);
                    series.get().getRatings().add(r);
                    user.get().getRatings().add(r);
                }
                long totalRating = (long) em.createQuery("select sum(rating) from Rating where series.id = :seriesId")
                        .setParameter("seriesId",series.get().getId())
                        .getSingleResult();

                series.get().setUserRating((double)totalRating / (series.get().getRatings().size()));
                done = true;
                em.flush();
            }
        }
        return done ? 1 : 0;
    }

    @Override
    public long getPostAuthorId(long postId) {
        Optional<SeriesReview> review = Optional.ofNullable(em.find(SeriesReview.class,postId));
        return review.map(SeriesReview::getUserId).orElse(-1L);
    }

    @Override
    public long getCommentAuthorId(long commentId) {
        Optional<SeriesReviewComment> comment = Optional.ofNullable(em.find(SeriesReviewComment.class,commentId));
        return comment.map(SeriesReviewComment::getUserId).orElse(-1L);
    }
}
