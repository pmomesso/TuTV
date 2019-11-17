package ar.edu.itba.paw.service;

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
                if(e.getAiring().before(new Date())){
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
    public SeriesServiceImpl(SeriesDao seriesDao, UserService userService) {
        this.seriesDao = seriesDao;
        this.userService = userService;
    }

    @Override
    public List<Series> searchSeries(String seriesName, String genreName, String networkName, int page) {
        String name = seriesName == null ? "" : seriesName;
        String genre = genreName == null ? "" : genreName;
        String network = networkName == null ? "" : networkName;
        return seriesDao.searchSeries(name,genre,network,page);
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
            });
        });
        return series;
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
    public List<Series> getAllSeriesByGenre(int id){
        return seriesDao.getSeriesByGenre(id);
    }

    @Override
    public Map<Genre,List<Series>> getSeriesByGenre() {
        return seriesDao.getBestSeriesByGenres();
    }

    @Override
    public Map<Genre,List<Series>> getSeriesByGenre(Long id, Long page) {
        return seriesDao.getBestSeriesByGenres(id, page);
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
    public List<Network> getAllNetworks() {
        return seriesDao.getAllNetworks();
    }

    @Override
    public boolean follows(long seriesId) throws UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        return seriesDao.userFollows(seriesId,user.getId());
    }

    @Override
    public void followSeries(long seriesId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        int result = seriesDao.followSeries(seriesId, user.getId());
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void unfollowSeries(long seriesId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        int result = seriesDao.unfollowSeries(seriesId, user.getId());
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void setViewedEpisode(long seriesId, long episodeId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(!this.follows(seriesId)){
            this.followSeries(seriesId);
        }
        int result = seriesDao.setViewedEpisode(episodeId, user.getId());
        if(result == 0) {
            throw new NotFoundException();
        }
    }
    @Override
    public void setViewedSeason(long seriesId, long seasonId) throws UnauthorizedException, NotFoundException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(!this.follows(seriesId)){
            this.followSeries(seriesId);
        }
        seriesDao.setViewedSeason(seasonId,user.getId());
    }

    @Override
    public void rateSeries(long seriesId, int rating) throws NotFoundException, UnauthorizedException, BadRequestException {
        if(rating > MAX_RATING || rating < MIN_RATING){
            throw new BadRequestException();
        }
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        int result = seriesDao.rateSeries(seriesId, user.getId(), rating);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void unviewEpisode(long episodeId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        int result = seriesDao.unviewEpisode(user.getId(), episodeId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }
    @Override
    public void unviewSeason(long seasonId) throws UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        seriesDao.unviewSeason(seasonId,user.getId());
    }
    @Override
    public void addSeriesReview(String body, long seriesId, boolean isSpam) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        seriesDao.createSeriesReview(body, seriesId, user.getId(), isSpam).orElseThrow(NotFoundException::new);
    }

    @Override
    public void likePost(long postId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        int result = seriesDao.likePost(user.getId(), postId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void unlikePost(long postId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        int result = seriesDao.unlikePost(user.getId(), postId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public SeriesReviewComment addCommentToPost(long commentPostId, String body) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);

        SeriesReview review = seriesDao.getSeriesReviewById(commentPostId).orElseThrow(NotFoundException::new);
        Object[] args = {review.getSeries().getName()};
        String message = messageSource.getMessage("index.commentNotification", args, LocaleContextHolder.getLocale());

        SeriesReviewComment s = seriesDao.addCommentToPost(commentPostId, body, user.getId()).orElseThrow(NotFoundException::new);
        if (!user.equals(review.getUser())) {
            notifyPoster(review.getUser(), review.getSeries(), message);
        }
        return s;
    }

    private void notifyPoster(User user, Series series, String message) {
        seriesDao.createNotification(user, series, message);
    }

    @Override
    @Transactional
    public void viewUntilEpisode(long episodeId) throws NotFoundException, UnauthorizedException {
        User u = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(!seriesDao.viewUntilEpisode(episodeId, u)) throw new NotFoundException();
    }

    @Override
    public void likeComment(long commentId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        int result = seriesDao.likeComment(user.getId(), commentId);
        if(result == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public void unlikeComment(long commentId) throws NotFoundException, UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        int result = seriesDao.unlikeComment(user.getId(), commentId);
        if(result == 0) {
            throw new NotFoundException();
        }
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
    public List<Episode> getWatchList() throws UnauthorizedException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        return seriesDao.getNextToBeSeen(user.getId());
    }

    @Override
    public List<Series> getRecentlyWatchedList(int number) throws UnauthorizedException, BadRequestException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
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
    public void addList(String name, long[] seriesId) throws UnauthorizedException{
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        Set<Series> series = new HashSet<>();
        for (long id : seriesId) {
            series.add(seriesDao.getSeriesById(id).orElseThrow(UnauthorizedException::new));
        }
        seriesDao.addList(user.getId(), name, series);
    }

    @Override
    @Transactional
    public void modifyList(long id, String name, long[] seriesIdList) throws UnauthorizedException, NotFoundException {
        User user = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        Set<Series> series = new HashSet<>();
        for (long seriesId : seriesIdList) {
            series.add(seriesDao.getSeriesById(seriesId).orElseThrow(UnauthorizedException::new));
        }
        int result = seriesDao.modifyList(id, user.getId(), name, series);
        if(result == 0) {
            throw new NotFoundException();
        }
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
}
