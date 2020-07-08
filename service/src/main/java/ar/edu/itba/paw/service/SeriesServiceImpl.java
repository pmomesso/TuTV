package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.MailService;
import ar.edu.itba.paw.interfaces.SeriesDao;
import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class SeriesServiceImpl implements SeriesService {

    private static final int MIN_RATING = 0;
    private static final int MAX_RATING = 5;

    @Autowired
    private SeriesDao seriesDao;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MailService mailService;


    private void setLoggedInUserRating(User u, Series s){
        for(Rating r : u.getRatings()){
            if(r.getSeries().getId() == s.getId()){
                s.setLoggedInUserRating(r.getRating());
                break;
            }
        }
    }
    private void setAiredSeasons(Series s){
        for(Season season : s.getSeasons()){
            for(Episode e : season.getEpisodes()){
                if(e.getAiring() != null && e.getAiring().before(new Date())){
                    season.setSeasonAired(true);
                    break;
                }
            }
        }
    }
    private void setViewedSeasons(User u){
        for(Episode e : u.getViewed()){
            e.getSeason().setEpisodesViewed(e.getSeason().getEpisodesViewed() + 1);
            if(e.getSeason().getEpisodes().size() == e.getSeason().getEpisodesViewed()){
                e.getSeason().setViewed(true);
            }
        }
    }
    @Autowired
    public SeriesServiceImpl(SeriesDao seriesDao, UserService userService,MessageSource messageSource) {
        this.seriesDao = seriesDao;
        this.userService = userService;
        this.messageSource = messageSource;
    }
    @Override
    public List<Series> searchSeries(String seriesName, String genreName, String networkName, int page, int pageSize) {
        String name = seriesName == null ? "" : seriesName;
        String genre = genreName == null ? "" : genreName;
        String network = networkName == null ? "" : networkName;
        return seriesDao.searchSeries(name,genre,network,page, pageSize);
    }

    @Override
    public List<Series> getSeriesByName(String name){
        return seriesDao.getSeriesByName(name);
    }

    @Override
    public Optional<Series> getSerieById(long id){
        Optional<Series> series = seriesDao.getSeriesById(id);
        Optional<User> u = userService.getLoggedUser();
        series.ifPresent(s -> {
            setAiredSeasons(s);
            u.ifPresent(user -> {
                //Guardo el rating del usuario.
                setLoggedInUserRating(user, s);
                //Marco las temporadas vistas.
                setViewedSeasons(user);
                setHasPreviousUnseenEpisodes(s, user);
            });
        });
        return series;
    }

    private void setHasPreviousUnseenEpisodes(Series series, User u) {
        for(Season season : series.getSeasons()) {
            for(Episode episode : season.getEpisodes()) {
                if(series.getSeasons().stream().anyMatch(s ->
                    s.getEpisodes().stream().anyMatch(e ->
                            (s.getSeasonNumber() < e.getSeason().getSeasonNumber()
                                    || (s.getSeasonNumber() == e.getSeason().getSeasonNumber() && e.getNumEpisode() < episode.getNumEpisode()))
                                    && !e.getViewers().contains(u)
                    )
                )) {
                    episode.setHasPreviousUnseenEpisodes(true);
                }
            }
        }
    }

    @Override
    public List<Series> getSeriesByGenreAndNumber(int genreId, int num) {
        return seriesDao.getBestSeriesByGenre(genreId, 0, num);
    }

    @Override
    public List<Series> getAllSeriesByGenre(String genreName) {
        return seriesDao.getSeriesByGenre(genreName);
    }

    @Override
    public List<Series> getAllSeriesByGenre(long id) {
        return seriesDao.getSeriesByGenre(id);
    }

    @Override
    public Map<Genre,List<Series>> getSeriesByGenre() {
        return seriesDao.getBestSeriesByGenres();
    }

    @Override
    public Map<Genre,List<Series>> getSeriesByGenre(Long id, Long page, Integer pageSize) {
        Map<Genre, List<Series>> ret = seriesDao.getBestSeriesByGenres(id, page, pageSize);
        return ret;
    }

    @Override
    public List<Series> getNewestSeries(int lowerNumber, int upperNumber) {
        return seriesDao.getNewSeries(lowerNumber, upperNumber);
    }

    @Override
    public List<Season> getSeasonsBySeriesId(long seriesId) {
        return seriesDao.getSeasonsBySeriesId(seriesId);
    }

    @Override
    public List<Genre> getAllGenres() {
        return seriesDao.getAllGenres();
    }

    @Override
    public Genre getGenreById(long genreId) throws NotFoundException {
        return seriesDao.getGenreById(genreId).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Network> getAllNetworks() {
        return seriesDao.getAllNetworks();
    }

    @Override
    public boolean follows(long seriesId) throws UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        return seriesDao.userFollows(seriesId,user.getId());
    }

    @Override
    public Series followSeries(long seriesId) throws UnauthorizedException, NotFoundException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        Series series = seriesDao.followSeries(seriesId, user.getId()).orElseThrow(NotFoundException::new);
        return series;
    }

    @Override
    public Series unfollowSeries(long seriesId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        Series series = seriesDao.unfollowSeries(seriesId, user.getId()).orElseThrow(NotFoundException::new);
        return series;
    }

    @Override
    public void setViewedEpisode(long seriesId, int seasonNumber, int episodeNumber) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(!this.follows(seriesId)){
            this.followSeries(seriesId);
        }
        int result = seriesDao.setViewedEpisode(seriesId, seasonNumber, episodeNumber, user.getId());
        if(result == 0) {
            throw new NotFoundException();
        }
    }
    @Override
    public void setViewedSeason(long seriesId, int seasonNumber) throws UnauthorizedException, NotFoundException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(!this.follows(seriesId)){
            this.followSeries(seriesId);
        }
        int result = seriesDao.setViewedSeason(seriesId,seasonNumber,user.getId());
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public Series rateSeries(long seriesId, int rating) throws NotFoundException, UnauthorizedException, BadRequestException {
        if(rating > MAX_RATING || rating < MIN_RATING){
            throw new BadRequestException();
        }
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        Series series = seriesDao.rateSeries(seriesId, user.getId(), rating).orElseThrow(NotFoundException::new);
        return series;
    }

    @Override
    public void unviewEpisode(long seriesId, int seasonNumber, int episodeNumber) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        int result = seriesDao.unviewEpisode(user.getId(), seriesId,seasonNumber,episodeNumber);
        if(result == 0) {
            throw new NotFoundException();
        }
    }
    @Override
    public void unviewSeason(long seriesId, int seasonNumber) throws UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        seriesDao.unviewSeason(seriesId,seasonNumber,user.getId());
    }
    @Override
    public Optional<SeriesReview> addSeriesReview(String body, long seriesId, boolean isSpam) throws UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        Optional<SeriesReview> optSeriesReview = seriesDao.createSeriesReview(body, seriesId, user.getId(), isSpam);
        return optSeriesReview;
    }

    @Override
    public SeriesReview likePost(long postId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        SeriesReview seriesReview = seriesDao.likePost(user.getId(), postId).orElseThrow(NotFoundException::new);
        return seriesReview;
    }

    @Override
    public SeriesReview unlikePost(long postId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        SeriesReview seriesReview = seriesDao.unlikePost(user.getId(), postId).orElseThrow(NotFoundException::new);
        return seriesReview;
    }

    @Override
    public SeriesReviewComment addCommentToPost(long commentPostId, String body, String baseUrl) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);

        SeriesReview review = seriesDao.getSeriesReviewById(commentPostId).orElseThrow(NotFoundException::new);
        Object[] args = {review.getSeries().getName()};
        String message = messageSource.getMessage("index.commentNotification", args, LocaleContextHolder.getLocale());

        SeriesReviewComment s = seriesDao.addCommentToPost(commentPostId, body, user.getId()).orElseThrow(NotFoundException::new);
        if (!user.equals(review.getUser())) {
            mailService.sendCommentResponseMail(s, baseUrl);
            seriesDao.createNotification(review.getUser(), review.getSeries(), message);
        }
        return s;
    }

    @Override
    @Transactional
    public void viewUntilEpisode(long seriesId, long episodeId) throws NotFoundException, UnauthorizedException {
        User u = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(!this.follows(seriesId)){
            this.followSeries(seriesId);
        }
        if(!seriesDao.viewUntilEpisode(episodeId, u)) throw new NotFoundException();
    }

    @Override
    public SeriesReviewComment likeComment(long commentId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        SeriesReviewComment result = seriesDao.likeComment(user.getId(), commentId).orElseThrow(NotFoundException::new);
        return result;
    }

    @Override
    public SeriesReviewComment unlikeComment(long commentId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        SeriesReviewComment result = seriesDao.unlikeComment(user.getId(), commentId).orElseThrow(NotFoundException::new);
        return result;
    }

    @Override
    public void removeComment(long commentId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(!user.getIsAdmin() && seriesDao.getCommentAuthorId(commentId) != user.getId()) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.removeComment(commentId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void removePost(long postId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(!user.getIsAdmin() && seriesDao.getPostAuthorId(postId) != user.getId()) {
            throw new UnauthorizedException();
        }
        int result = seriesDao.removePost(postId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Episode> getWatchList(int page, int pageSize) throws UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        return seriesDao.getNextToBeSeen(user.getId(), page, pageSize);
    }

    @Override
    public List<Series> getRecentlyWatchedList(Long userId, int number) throws NotFoundException, BadRequestException {
        User user = userService.findById(userId).orElseThrow(NotFoundException::new);
        if(number <= 0) {
            throw new BadRequestException();
        }
        return seriesDao.getRecentlyWatched(user.getId(), number).orElseThrow(BadRequestException::new);
    }

    @Override
    public List<Series> getAddedSeries() throws UnauthorizedException, NotFoundException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        Optional<List<Series>> series = seriesDao.getAddedSeries(user.getId());
        return series.orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Series> getAddedSeries(long userId) throws NotFoundException {
        return seriesDao.getAddedSeries(userId).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Episode> getUpcomingEpisodes() throws UnauthorizedException, NotFoundException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        Optional<List<Episode>> episodes = seriesDao.getUpcomingEpisodes(user.getId());
        return episodes.orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public Optional<SeriesList> addList(String name, long[] seriesId) throws UnauthorizedException{
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        Set<Series> series = new HashSet<>();
        if(seriesId != null) {
            for (long id : seriesId) {
                series.add(seriesDao.getSeriesById(id).orElseThrow(UnauthorizedException::new));
            }
        }
        return seriesDao.addList(user.getId(), name, series);
    }

    @Override
    @Transactional
    public Optional<Boolean> getLoggedInUserLikesSeriesReview(long seriesReviewId) {
        Optional<User> loggedInUser = userService.getLoggedUser();
        if(!loggedInUser.isPresent()) {
            return Optional.empty();
        }
        return seriesDao.userLikesSeriesReview(loggedInUser.get(), seriesReviewId);
    }

    @Override
    @Transactional
    public int addSeriesToList(long id, long seriesId) throws UnauthorizedException, NotFoundException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        int result = seriesDao.addSeriesToList(id, seriesId);
        if(result == -1) throw new NotFoundException();
        return result;
    }

    @Override
    @Transactional
    public Optional<SeriesList> modifyList(long id, String name, long[] seriesIdList) throws UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        Set<Series> series = null;
        if(seriesIdList != null){
            series = new HashSet<>();
            for (long seriesId : seriesIdList) {
                series.add(seriesDao.getSeriesById(seriesId).orElseThrow(UnauthorizedException::new));
            }

        }
        return seriesDao.modifyList(id, user.getId(), name, series);
    }

    @Override
    @Transactional
    public void removeList(long id) throws UnauthorizedException, NotFoundException {
        userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        int result = seriesDao.removeList(id);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 ? * *") /* Corre a las 12am todos los d￿ias*/
    @Transactional
    public void createNotificationsForNewEpisodes() {
        List<Episode> toBeReleased = seriesDao.getToBeReleasedEpisodes();
        for(Episode episode : toBeReleased) {
            Series series = episode.getSeason().getSeries();
            for(User user : series.getUserFollowers()) {
                /* Creo una notificacion y la persisto*/
                Object[] args = {series.getName()};
                String message = messageSource.getMessage("index.releaseNotification", args, LocaleContextHolder.getLocale());
                seriesDao.createNotification(user, series, message);
            }
        }
    }

    @Override
    @Transactional
    public Optional<SeriesReview> getSeriesReviewById(Long seriesReviewId) {
        Optional<SeriesReview> seriesReview = seriesDao.getSeriesReviewById(seriesReviewId);
        return seriesReview;
    }

    @Override
    @Transactional
    public Optional<SeriesReview> reviewWithComment(Long commentId) {
        return seriesDao.reviewWithComment(commentId);
    }

    @Override
    public Optional<SeriesReviewComment> getSeriesReviewCommentById(Long commentId) {
        return Optional.ofNullable(seriesDao.getCommentById(commentId));
    }

    @Override
    public Optional<Series> serieWithReview(Long seriesReviewId) {
        return seriesDao.serieWithReview(seriesReviewId);
    }

}
