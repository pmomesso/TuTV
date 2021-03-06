package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.model.*;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class SeriesHibernateDao implements SeriesDao {

    private static final int PAGE_SIZE = 24;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Series> searchSeries(String seriesName, Long genreId, Long networkId, int page, int pageSize) {
        //Valores por defecto
        page = page > 0 ? page : 1;
        pageSize = pageSize <= 0 || pageSize > PAGE_SIZE ? 24 : pageSize;
        seriesName = seriesName != null ? seriesName : "";

        genreId = genreId == null || genreId <= 0 ? -1 : genreId;
        networkId = networkId == null || networkId <= 0 ? -1 : networkId;

        return em.createNativeQuery("SELECT DISTINCT(series.*) FROM series INNER JOIN hasgenre ON series.id = hasgenre.seriesid INNER JOIN genres ON hasgenre.genreid = genres.id INNER JOIN network ON series.network_id = network.id WHERE LOWER(series.name) like ? " +
                " AND (network.id = ? OR ? = -1) AND (genres.id = ? OR ? = -1) LIMIT ? OFFSET ?", Series.class)
                .setParameter(1, "%" + seriesName.toLowerCase() + "%")
                .setParameter(2, networkId)
                .setParameter(3, networkId)
                .setParameter(4, genreId)
                .setParameter(5, genreId)
                .setParameter(6, pageSize)
                .setParameter(7, (page - 1)* pageSize)
                .getResultList();
    }

    @Override
    public List<Series> getSeriesByName(String seriesName) {
        final TypedQuery<Series> query = em.createQuery("select s from Series as s " +
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
        for(int i = lowerLimit; i < upperLimit && i < distinctSeries.length; i++){
            result.add((Series)distinctSeries[i]);
        }
        return result;
    }

    private List<Series> getBestSeriesByGenre(Genre genre, Long page, Integer pageSize) {
        //int bestSize = PAGE_SIZE / 3;
        List<Series> seriesList = em.createNativeQuery("SELECT * " +
                "FROM (series JOIN hasGenre ON hasgenre.seriesid = series.id JOIN genres ON hasgenre.genreid = genres.id LEFT JOIN network ON network.id = series.network_id) " +
                "WHERE genreid = ?" +
                "ORDER BY series.followers,series.id DESC LIMIT ? OFFSET ?",Series.class)
                .setParameter(1, genre.getId())
                .setParameter(2, pageSize)
                .setParameter(3, (page - 1) * pageSize)
                .getResultList();

        TypedQuery<Long> countQuery = em.createQuery("select count(*) from Series as s inner join s.genres as genres " +
                "WHERE genres.id = :id", Long.class);
        countQuery.setParameter("id", genre.getId());
        Long count = countQuery.getSingleResult();

        genre.setPage(page);
        genre.setArePrevious(((page - 1) * pageSize) > (pageSize - 1));
        int added = ((count % pageSize) == 0) ? 0 : 1;
        genre.setAreNext(page < (count/pageSize + added));

        return seriesList;
    }

    @Override
    public Map<Genre,List<Series>> getBestSeriesByGenres() {
        Map<Genre,List<Series>> genreMap = new TreeMap<>(Comparator.comparing(Genre::getName));
        for(Genre g : getAllGenres()){
            genreMap.put(g, getBestSeriesByGenre(g, 1L, PAGE_SIZE));
        }
        return genreMap;
    }

    @Override
    public Map<Genre,List<Series>> getBestSeriesByGenres(Long id, Long page, Integer pageSize) {
        Map<Genre,List<Series>> genreMap = new TreeMap<>(Comparator.comparing(Genre::getName));
        for(Genre g : getAllGenres()){
            if(g.getId().equals(id)) {
                genreMap.put(g, getBestSeriesByGenre(g, page, pageSize));
                break;
            }
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
    public List<Episode> getNextToBeSeen(long userId, int page, int pageSize) {
        if(pageSize <= 0 || pageSize >= 24) {
            pageSize = 24;
        }
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        List<Episode> nextToBeSeen = new ArrayList<>();
        if(user.isPresent()){
            List<Season> seasons = new ArrayList<>();
            List<Episode> episodes = new ArrayList<>();
            boolean done;
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
        List<Episode> episodePage = new ArrayList<>();
        for(int i = (page - 1) * pageSize; i < page * pageSize && i < nextToBeSeen.size(); i++){
            episodePage.add(nextToBeSeen.get(i));
        }
        return episodePage;
    }

    @Override
    public Optional<List<Series>> getRecentlyWatched(long userId, int number) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Set<Series> recentlyWatched = new HashSet<>();
        int i = 0;
        boolean added;
        if(user.isPresent()){
            for(Episode e: user.get().getViewed()){
                added = recentlyWatched.add(e.getSeason().getSeries());
                if(added && i++ >= number){break;}
            }
        }

        return Optional.of(new ArrayList<>(recentlyWatched));
    }

    @Override
    public Optional<List<Series>> getAddedSeries(long userId, int page, int pagesize) {
        return Optional.of(em.createNativeQuery("SELECT series.* FROM follows INNER JOIN series on follows.seriesid = series.id WHERE follows.userid = ? LIMIT ? OFFSET ?",Series.class)
                .setParameter(1,userId)
                .setParameter(2,(pagesize))
                .setParameter(3,(page - 1) * pagesize).getResultList());
    }

    @Override
    public Optional<List<Episode>> getUpcomingEpisodes(long userId, int page, int pagesize) {
        TypedQuery<Episode> query = em.createQuery("from Episode as e " +
                "where e.aired >= :today " +
                "and exists(from User as u " +
                "where u.id = :userId and u in elements(e.season.series.userFollowers))",Episode.class);
        query.setParameter("userId", userId);
        query.setParameter("today",new Date(), TemporalType.DATE);
        query.setFirstResult((page - 1) * pagesize);
        query.setMaxResults(pagesize);
        return Optional.of(query.getResultList());
    }

    @Override
    public Optional<Genre> getGenreById(long genreId) {
        return Optional.of(em.find(Genre.class ,genreId));
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
    public Optional<Series> followSeries(long seriesId, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<Series> series = Optional.ofNullable(em.find(Series.class,seriesId));
        if(user.isPresent() && series.isPresent()){
            if(!series.get().getUserFollowers().contains(user.get())) {
                series.get().addUserFollower(user.get());
                series.get().setFollowers(series.get().getUserFollowers().size());
                em.flush();
            }
        }
        return series;
    }

    @Override
    public Optional<Series> unfollowSeries(long seriesId, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<Series> series = Optional.ofNullable(em.find(Series.class,seriesId));
        if(user.isPresent() && series.isPresent()){
            if(series.get().getUserFollowers().contains(user.get())) {
                series.get().removeUserFollower(user.get());
                series.get().setFollowers(series.get().getUserFollowers().size());
            }
            em.flush();
        }
        return series;
    }

    @Override
    public int setViewedEpisode(long seriesId,int seasonNumber,int episodeNumber, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        TypedQuery<Episode> query = em.createQuery("from Episode as e where e.season.series.id = :seriesId and e.season.seasonNumber = :seasonNumber and e.numEpisode = :numEpisode",Episode.class);
        query.setParameter("seriesId",seriesId);
        query.setParameter("seasonNumber",seasonNumber);
        query.setParameter("numEpisode",episodeNumber);
        List<Episode> resultList = query.getResultList();
        Optional<Episode> episode = resultList.stream().findFirst();
        int updated = 0;
        if(user.isPresent() && episode.isPresent()){
            updated = 1;
            if(!episode.get().getViewers().contains(user.get())) {
                episode.get().addViewer(user.get());
            }
            em.flush();
        }
        return updated;
    }

    @Override
    public int setViewedSeason(long seriesId, int seasonNumber, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        TypedQuery<Season> query = em.createQuery("from Season as s where s.series.id = :seriesId and s.seasonNumber = :seasonNumber",Season.class);
        query.setParameter("seriesId",seriesId);
        query.setParameter("seasonNumber",seasonNumber);
        List<Season> resultList = query.getResultList();
        Optional<Season> season = resultList.stream().findFirst();
        int updated = 0;
        if(user.isPresent() && season.isPresent()){
            updated = 1;
            for(Episode episode : season.get().getEpisodes()){
                if(!episode.getViewers().contains(user.get())) {
                    episode.addViewer(user.get());
                    updated++;
                }
            }
            em.flush();
        }
        return updated;
    }

    @Override
    public int unviewSeason(long seriesId, int seasonNumber, long userId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        TypedQuery<Season> query = em.createQuery("from Season as s where s.series.id = :seriesId and s.seasonNumber = :seasonNumber",Season.class);
        query.setParameter("seriesId",seriesId);
        query.setParameter("seasonNumber",seasonNumber);
        Optional<Season> season = Optional.ofNullable(query.getSingleResult());
        int updated = 0;
        if(user.isPresent() && season.isPresent()){
            updated = 1;
            for(Episode episode : season.get().getEpisodes()){
                if(episode.getViewers().contains(user.get())) {
                    episode.removeViewer(user.get());
                    updated++;
                }
            }
            em.flush();
        }
        return updated;
    }

    @Override
    public int unviewEpisode(long userId, long seriesId, int seasonNumber, int episodeNumber) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        TypedQuery<Episode> query = em.createQuery("from Episode as e where e.season.series.id = :seriesId and e.season.seasonNumber = :seasonNumber and e.numEpisode = :numEpisode",Episode.class);
        query.setParameter("seriesId",seriesId);
        query.setParameter("seasonNumber",seasonNumber);
        query.setParameter("numEpisode",episodeNumber);
        List<Episode> resultList = query.getResultList();
        Optional<Episode> episode = resultList.stream().findFirst();
        int updated = 0;
        if(user.isPresent() && episode.isPresent()){
            updated = 1;
            if(episode.get().getViewers().contains(user.get())) {
                episode.get().removeViewer(user.get());
            }
            em.flush();
        }
        return updated;
    }

    @Override
    public Optional<SeriesReview> createSeriesReview(String body, long seriesId, long userId, boolean isSpam) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<Series> series = Optional.ofNullable(em.find(Series.class,seriesId));
        if(!user.isPresent() || !series.isPresent()){
            return Optional.empty();
        }
        SeriesReview review = new SeriesReview(body,series.get(),user.get(),isSpam);
        em.persist(review);
        user.get().getSeriesReviews().add(review);
        series.get().getSeriesReviewList().add(review);
        return Optional.of(review);
    }

    @Override
    public Optional<SeriesReview> likePost(long userId, long postId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<SeriesReview> review = Optional.ofNullable(em.find(SeriesReview.class,postId));
        if(user.isPresent() && review.isPresent()){
            if(!review.get().getLikes().contains(user.get())) {
                review.get().addLike(user.get());
                review.get().setNumLikes(review.get().getLikes().size());
                em.flush();
            }
        }
        return review;
    }

    @Override
    public Optional<SeriesReview> unlikePost(long userId, long postId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<SeriesReview> review = Optional.ofNullable(em.find(SeriesReview.class,postId));
        if(user.isPresent() && review.isPresent()){
            if(review.get().getLikes().contains(user.get())) {
                review.get().removeLike(user.get());
                review.get().setNumLikes(review.get().getLikes().size());
            }
            em.flush();
        }
        return review;
    }

    @Override
    public Optional<SeriesReviewComment> addCommentToPost(long commentPostId, String commentBody, long commentUserId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,commentUserId));
        Optional<SeriesReview> review = Optional.ofNullable(em.find(SeriesReview.class,commentPostId));
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
    public Optional<Notification> createNotification(User user, Series series, String message) {
        Notification n = new Notification(user, series, message);
        em.persist(n);
        return Optional.of(n);
    }

    @Override
    public Optional<SeriesReviewComment> likeComment(long userId, long commentId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<SeriesReviewComment> comment = Optional.ofNullable(em.find(SeriesReviewComment.class,commentId));
        if(user.isPresent() && comment.isPresent()){
            if(!comment.get().getLikes().contains(user.get())) {
                comment.get().addLike(user.get());
                comment.get().setNumLikes(comment.get().getLikes().size());
            }
            em.flush();
        }
        return comment;
    }

    @Override
    public Optional<SeriesReviewComment> unlikeComment(long userId, long commentId) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        Optional<SeriesReviewComment> comment = Optional.ofNullable(em.find(SeriesReviewComment.class,commentId));
        if(user.isPresent() && comment.isPresent()){
            if(comment.get().getLikes().contains(user.get())) {
                comment.get().removeLike(user.get());
                comment.get().setNumLikes(comment.get().getLikes().size());
            }
            em.flush();
        }
        return comment;
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
    public Optional<Series> rateSeries(long seriesId, long userId, int rating) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        boolean done = false;
        Optional<Series> series = Optional.empty();
        if(user.isPresent()){
            series = Optional.ofNullable(em.find(Series.class,seriesId));
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
        return series;
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

    @Override
    public Optional<SeriesList> addList(long userId, String name, Set<Series> series) {
        Optional<User> user = Optional.ofNullable(em.find(User.class,userId));
        if (user.isPresent()) {
            SeriesList list = new SeriesList(user.get(), name, series);
            em.persist(list);
            user.get().getLists().add(list);
            for (Series currSeries : series) {
                currSeries.getSeriesList().add(list);
            }
            return Optional.of(list);
        }
        return Optional.empty();
    }

    @Override
    public Optional<SeriesList> modifyList(long id, long userId, String name, Set<Series> series) {
        Optional<SeriesList> list = Optional.ofNullable(em.find(SeriesList.class, id));
        if (list.isPresent()) {
            if(name != null && !name.isEmpty()){
                list.get().setName(name);
            }
            if(series != null){
                list.get().setSeries(series);
            }
            em.persist(list.get());
            return list;
        }
        return Optional.empty();
    }

    @Override
    public int removeList(long id) {
        Optional<SeriesList> list = Optional.ofNullable(em.find(SeriesList.class, id));
        int updated = 0;
        if(list.isPresent()) {
            em.remove(list.get());
            updated++;
        }
        return updated;
    }

    @Override
    public Optional<SeriesReview> getSeriesReviewById(long commentPostId) {
        return Optional.ofNullable(em.find(SeriesReview.class, commentPostId));
    }

    @Override
    public boolean viewUntilEpisode(long episodeId, User u) {
        Episode episode = em.find(Episode.class, episodeId);
        if(episode == null) {
            return false;
        }
        /*Get the episodes that come before the current one and are not viewed by the current user*/
        final TypedQuery<Episode> query = em.createQuery("SELECT e " +
                "FROM Episode e " +
                "WHERE (e.season.seasonNumber < :seasonNumber " +
                "OR (e.numEpisode <= :numEpisode AND e.season.seasonNumber = :seasonNumber)) " +
                "AND e.season.series.id = :seriesId " +
                "AND :userId NOT IN (SELECT id FROM User u WHERE u IN elements(e.viewers))", Episode.class)
                .setParameter("seasonNumber", episode.getSeason().getSeasonNumber())
                .setParameter("numEpisode", episode.getNumEpisode())
                .setParameter("seriesId", episode.getSeason().getSeries().getId())
                .setParameter("userId", u.getId());
        List<Episode> episodeList = query.getResultList();
        /*Set the episodes as viewed*/
        Set<Episode> episodesViewed = u.getViewed();
        episodesViewed.addAll(episodeList);
        for(Episode e : episodeList) {
            e.getViewers().add(u);
        }
        return true;
    }

    @Override
    public List<Episode> getToBeReleasedEpisodes() {
        /* Query para conseguir todos los episodios que saldr???an en 1 semana*/
        TypedQuery<Episode> query = em.createQuery("FROM Episode e " +
                "WHERE year(e.aired) = year(current_date()) " +
                "AND month(e.aired) = month(current_date()) " +
                "AND day(e.aired) - day(current_date) = 7", Episode.class);
        List<Episode> toBeReleased = query.getResultList();
        return toBeReleased;
    }

    @Override
    public Optional<Boolean> userLikesSeriesReview(User user, long seriesReviewId) {
        Optional<SeriesReview> seriesReview = Optional.ofNullable(em.find(SeriesReview.class, seriesReviewId));
        Optional<Boolean> ret = Optional.empty();
        if(seriesReview.isPresent()) {
            ret = Optional.of(Boolean.valueOf(seriesReview.get().getLikes().contains(user)));
        }
        return ret;
    }

    @Override
    public Optional<SeriesReview> reviewWithComment(Long commentId) {
        TypedQuery<SeriesReview> query = em.createQuery("select sr from SeriesReview sr inner join sr.comments c " +
                "where c.id = :commentId", SeriesReview.class).setParameter("commentId", commentId);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public SeriesReviewComment getCommentById(Long commentId) {
        return em.createQuery("from SeriesReviewComment src where src.id = :commentId", SeriesReviewComment.class)
                .setParameter("commentId", commentId).getSingleResult();
    }

    @Override
    public Optional<Series> serieWithReview(Long seriesReviewId) {
        TypedQuery<Series> query = em.createQuery("select s from Series s inner join s.seriesReviewList sr " +
                "where sr.id = :seriesReviewId", Series.class).setParameter("seriesReviewId", seriesReviewId);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public int addSeriesToList(long id, long seriesId) {
        Optional<SeriesList> list = Optional.ofNullable(em.find(SeriesList.class, id));
        if(!list.isPresent()) return -1;
        Optional<Series> series = Optional.ofNullable(em.find(Series.class, seriesId));
        if(!series.isPresent()) return -1;
        int prevSize = list.get().getSeries().size();
        list.get().getSeries().add(series.get());
        int newSize = list.get().getSeries().size();
        em.persist(list.get());
        return newSize - prevSize;
    }

    @Override
    public Optional<List<Series>> getSeriesInList(Long listId, int page, int pageSize) {

        page = page <= 0 ? 1 : page;
        pageSize = pageSize <= 0 ? PAGE_SIZE : pageSize;

        Query q = em.createNativeQuery("SELECT DISTINCT(series.*)\n" +
                "FROM series INNER JOIN seriesList ON series.id = seriesList.seriesid\n" +
                "WHERE seriesList.listid = ?\n" +
                "ORDER BY series.name\n" +
                "LIMIT ? OFFSET ?", Series.class);
        q.setParameter(1, listId).setParameter(2, pageSize).setParameter(3, (page-1)*pageSize);
        return Optional.of(q.getResultList());
    }

    @Override
    public Optional<List<SeriesReview>> getSeriesReviewList(Long seriesId) {
        TypedQuery<SeriesReview> query = em.createQuery("select sr from SeriesReview as sr " +
                "where sr.series.id = :seriesId order by sr.id desc", SeriesReview.class);
        query.setParameter("seriesId", seriesId);
        Optional<List<SeriesReview>> resultList = Optional.ofNullable(query.getResultList());
        return resultList;
    }

    @Override
    public int viewUntilEpisode(Long seriesId, Integer seasonNumber, Integer episodeNumber, User user) {
        TypedQuery<Long> countQuery = em.createQuery("select count(*) from Series as s inner join s.seasons as sea inner join sea.episodes as ep " +
                "where s.id = :seriesId and sea.seasonNumber = :seasonNumber and ep.numEpisode = :episodeNumber", Long.class)
                .setParameter("seriesId", seriesId)
                .setParameter("seasonNumber", seasonNumber)
                .setParameter("episodeNumber", episodeNumber);

        Long count = countQuery.getSingleResult();
        if(count == 0) {
            return 0;
        }

        Series s = em.find(Series.class, seriesId);
        s.getSeasons().stream().forEach(season -> {
            if(season.getSeasonNumber() <= seasonNumber) {
                season.getEpisodes().stream().forEach(ep -> {
                    if(season.getSeasonNumber() < seasonNumber || ep.getNumEpisode() <= episodeNumber) {
                        if(!ep.getViewers().contains(user)) {
                            ep.addViewer(user);
                        }
                    }
                });
            }
        });
        em.flush();
        return 1;
    }

    @Override
    public int viewUntilSeason(Long seriesId, Integer seasonNumber, User user) {
        TypedQuery<Long> countQuery = em.createQuery("select count(*) from Series as s inner join s.seasons as sea " +
                "where s.id = :seriesId and sea.seasonNumber = :seasonNumber", Long.class)
                .setParameter("seriesId", seriesId)
                .setParameter("seasonNumber", seasonNumber);

        Long count = countQuery.getSingleResult();
        if(count == 0) {
            return 0;
        }

        Series s = em.find(Series.class, seriesId);
        s.getSeasons().stream().forEach(season -> {
            if(season.getSeasonNumber() <= seasonNumber) {
                season.getEpisodes().stream().forEach(ep -> {
                    if(!ep.getViewers().contains(user)) {
                        ep.addViewer(user);
                    }
                });
            }
        });
        em.flush();
        return 1;
    }


}
