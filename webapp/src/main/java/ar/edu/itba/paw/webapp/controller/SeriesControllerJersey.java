package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.*;

@Path("series")
public class SeriesControllerJersey {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private SeriesService seriesService;
    @Autowired
    private UserService userService;
    @Autowired
    private Validator validator;

    @GET
    @Path("/featured")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMainPageJson() {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);
        List<SeriesDTO> featured = seriesService.getNewestSeries(0, 4).stream()
                .map(e -> new SeriesDTO(e, uriInfo)).collect(Collectors.toList());
        return ok(new GenericEntity<List<SeriesDTO>>(featured) {}).cacheControl(cc).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSeries(@QueryParam("name") String name, @QueryParam("genre") String genre, @QueryParam("network") String network, @QueryParam("page") Integer page) {

        page = page == null || page < 1 ? 1 : page;
        List<Series> results = seriesService.searchSeries(name,genre,network,page);
        SeriesDTO[] seriesDTOList = new SeriesDTO[results.size()];
        for(int i = 0; i < results.size(); i++){
            seriesDTOList[i] = new SeriesDTO(results.get(i), uriInfo);
        }
        ResponseBuilder rb = ok(seriesDTOList);
        boolean next = seriesService.searchSeries(name,genre,network,page + 1).size() > 0;
        if(next || page > 1) {
            UriBuilder nextUri = uriInfo.getAbsolutePathBuilder();
            UriBuilder prevUri = uriInfo.getAbsolutePathBuilder();
            if(name != null && name.length() > 0){
                nextUri.queryParam("name", name);
                prevUri.queryParam("name", name);
            }
            if(genre != null && genre.length() > 0){
                nextUri.queryParam("genre", genre);
                prevUri.queryParam("genre", genre);
            }
            if(network != null && network.length() > 0){
                nextUri.queryParam("network", network);
                prevUri.queryParam("network", network);
            }
            String previousPath = prevUri.queryParam("page", page - 1).build().toString() + " , rel = prev";
            String nextPath = nextUri.queryParam("page", page + 1).build().toString() + " , rel = next";
            rb.header("Link", (next ? nextPath : "") + ((next && page > 1) ? " ; " : "") + (page > 1 ? previousPath : ""));
        }
        return rb.build();
    }

    @GET
    @Path("/{seriesId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisode(@PathParam("seriesId") Long seriesId) {
        Optional<Series> optSeries = seriesService.getSerieById(seriesId);
        if (!optSeries.isPresent()) {
            return status(Status.NOT_FOUND).build();
        }
        Series series = optSeries.get();
        SeriesDTO seriesDTO = new SeriesDTO(series, userService.getLoggedUser(), uriInfo);
        return ok(seriesDTO).build();
    }

    @GET
    @Path("/{seriesId}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeriesComments(@PathParam("seriesId") Long seriesId) {
        Optional<Series> optSeries = seriesService.getSerieById(seriesId);
        if(!optSeries.isPresent()) {
            return status(Status.NOT_FOUND).build();
        }
        Series series = optSeries.get();
        SeriesReviewsDTO seriesReviewsDTO = new SeriesReviewsDTO(series, userService.getLoggedUser());
        return ok(seriesReviewsDTO).build();
    }

    @POST
    @Path("/{seriesId}/reviews")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReview(@PathParam("seriesId") Long seriesId,
                                 @Valid SeriesReviewNewDTO seriesReviewNewDTO) {

        Set<ConstraintViolation<SeriesReviewNewDTO>> violations = validator.validate(seriesReviewNewDTO);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }
        try {
            Optional<SeriesReview> optSeriesReview = seriesService.addSeriesReview(seriesReviewNewDTO.getBody(), seriesId,
                    seriesReviewNewDTO.getIsSpam());
            if(!optSeriesReview.isPresent()) {
                return status(Status.NOT_FOUND).build();
            }
            return ok(new SeriesReviewDTO(optSeriesReview.get(), userService.getLoggedUser())).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        }

    }

    @POST
    @Path("/{seriesId}/reviews/{seriesReviewId}/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(@PathParam("seriesId") Long seriesId, @PathParam("seriesReviewId") Long seriesReviewId,
                                  @Valid SeriesReviewCommentNewDTO seriesReviewCommentNewDTO) {

        Set<ConstraintViolation<SeriesReviewCommentNewDTO>> violations = validator.validate(seriesReviewCommentNewDTO);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }

        Optional<Series> series = seriesService.serieWithReview(seriesReviewId);
        if(!series.isPresent() || series.get().getId() != seriesId) {
            return status(Status.BAD_REQUEST).build();
        }

        //Todo: fix baseUrl
        try {
            SeriesReviewComment seriesReviewComment = seriesService.addCommentToPost(seriesReviewId, seriesReviewCommentNewDTO.getBody(), null);
            return ok(new SeriesReviewCommentDTO(seriesReviewComment, userService.getLoggedUser())).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        }
    }

    @PUT
    @Path("/{seriesId}/reviews/{seriesReviewId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSeriesReview(@PathParam("seriesId") Long seriesId, @PathParam("seriesReviewId") Long seriesReviewId,
                                       @Valid SeriesReviewStateDTO seriesReviewStateDTO) {
        Set<ConstraintViolation<SeriesReviewStateDTO>> violations = validator.validate(seriesReviewStateDTO);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }
        Optional<Series> series = seriesService.getSerieById(seriesId);
        if(!series.isPresent() || !seriesService.serieWithReview(seriesReviewId).isPresent()) {
            return status(Status.NOT_FOUND).build();
        }
        try {
            if(seriesReviewStateDTO.getLoggedInUserLikes()) {
                seriesService.likePost(seriesReviewId);
            } else {
                seriesService.unlikePost(seriesReviewId);
            }
            return accepted(new SeriesReviewDTO(seriesService.getSeriesReviewById(seriesReviewId).get(), userService.getLoggedUser())).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{seriesId}/reviews/{seriesReviewId}/comments/{commentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSeriesReviewComment(@PathParam("seriesId") Long seriesId, @PathParam("seriesReviewId") Long seriesReviewId,
            @PathParam("commentId") Long commentId, @Valid SeriesReviewCommentStateDTO seriesReviewCommentStateDTO) {

        Set<ConstraintViolation<SeriesReviewCommentStateDTO>> violations = validator.validate(seriesReviewCommentStateDTO);
        if(!violations.isEmpty()) {
            return status(Status.NOT_FOUND).build();
        }

        if(!seriesService.reviewWithComment(commentId).isPresent()) {
            return status(Status.NOT_FOUND).build();
        }
        try {
            if (seriesReviewCommentStateDTO.getLoggedInUserLikes()) {
                seriesService.likeComment(commentId);
            } else {
                seriesService.unlikeComment(commentId);
            }
            return accepted(new SeriesReviewCommentDTO(seriesService.getSeriesReviewCommentById(commentId).get(), userService.getLoggedUser())).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{seriesId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSeries(@PathParam("seriesId") Long seriesId, @Valid SerieStateDTO serieStateDTO) {

        Set<ConstraintViolation<SerieStateDTO>> violations = validator.validate(serieStateDTO);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }

        try {
            if(serieStateDTO.getLoggedInUserFollows()) {
                seriesService.followSeries(seriesId);
            } else {
                seriesService.unfollowSeries(seriesId);
            }
            if(serieStateDTO.getLoggedInUserRating() != null) {
                seriesService.rateSeries(seriesId, serieStateDTO.getLoggedInUserRating());
            }
            return accepted().entity(new SeriesDTO(seriesService.getSerieById(seriesId).get(), userService.getLoggedUser(), uriInfo)).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        } catch (BadRequestException e) {
            return status(Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/genres")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenreList() {
        List<GenreDTO> genres = seriesService.getAllGenres().stream()
                .map(g -> new GenreDTO(g, uriInfo)).collect(Collectors.toList());
        return ok(new GenericEntity<List<GenreDTO>>(genres) {}).build();
        /*
        GenreListDTO genreListDTO = new GenreListDTO(seriesService.getAllGenres());
        genreListDTO.getGenres().stream().forEach(genreDTO -> genreDTO.setSeries(null));
        ResponseBuilder rb = ok(genreListDTO);
        return rb.build();
         */
    }

    @GET
    @Path("/genres/{genreId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeriesInGenre(@PathParam("genreId") Long genreId, @QueryParam("page") Integer page) {
        Map<Genre, List<Series>> map;
        if(page == null) {
            map = seriesService.getSeriesByGenre(genreId, 1L);
        } else {
            map = seriesService.getSeriesByGenre(genreId, Long.valueOf(page));
        }
        Optional<Genre> optGenre = map.keySet().stream().findFirst();
        if(!optGenre.isPresent()) {
            return status(Status.NOT_FOUND).build();
        }
        Genre g = optGenre.get();
        ResponseBuilder rb = ok(new GenreDTO(g, map.get(g), uriInfo));
        if(g.isAreNext() || g.isArePrevious()) {
            rb.header("Link", (g.isAreNext() ? (uriInfo.getAbsolutePathBuilder().queryParam("page", g.getPage() + 1).build().toString() + " , rel = next") : "")
                    + ((g.isAreNext() && g.isArePrevious()) ? " ; " : "") + (g.isArePrevious() ? (uriInfo.getAbsolutePathBuilder().queryParam("page", g.getPage() - 1).build().toString() + " , rel = prev") : ""));
        }
        return rb.build();
    }

    @GET
    @Path("/{seriesId}/seasons")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeriesSeasons(@PathParam("seriesId") Long seriesId) {
        Optional<User> loggedUser = userService.getLoggedUser();
        List<SeasonDTO> seasons = seriesService.getSeasonsBySeriesId(seriesId)
                                    .stream().map(season -> new SeasonDTO(season, loggedUser)).collect(Collectors.toList());
        return ok(new GenericEntity<List<SeasonDTO>>(seasons) {}).build();
    }

    @GET
    @Path("/{seriesId}/seasons/{seasonNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeriesSeason(@PathParam("seriesId") Long seriesId, @PathParam("seasonNumber") Integer seasonNumber) {
        Optional<User> loggedUser = userService.getLoggedUser();
        List<Season> seasons = seriesService.getSeasonsBySeriesId(seriesId);
        Optional<Season> season = seasons.stream().filter(s -> seasonNumber == s.getSeasonNumber()).findFirst();
        if(!season.isPresent()) {
            return status(Status.NOT_FOUND).build();
        }
        SeasonDTO seasonDTO = new SeasonDTO(season.get(), loggedUser);
        return ok(seasonDTO).build();
    }

    @PUT
    @Path("/{seriesId}/seasons/{seasonNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewSeason(@PathParam("seriesId") Long seriesId, @PathParam("seasonNumber") Integer seasonNumber,
                               @Valid ViewedResourceDTO viewedResourceDTO){

        Set<ConstraintViolation<ViewedResourceDTO>> violations = validator.validate(viewedResourceDTO);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }

        if(seriesId < 0 || seasonNumber < 0){
            return status(Status.BAD_REQUEST).build();
        }

        try {
            if(viewedResourceDTO.getViewedByUser()){
                seriesService.setViewedSeason(seriesId,seasonNumber);
            }
            else{
                seriesService.unviewSeason(seriesId,seasonNumber);
            }
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }

        return status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{seriesId}/seasons/{seasonNumber}/episodes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeasonEpisodes(@PathParam("seriesId") Long seriesId, @PathParam("seasonNumber") Integer seasonNumber) {
        Optional<Series> series = seriesService.getSerieById(seriesId);
        if(!series.isPresent()) return status(Status.NOT_FOUND).build();

        Optional<Season> season = series.get().getSeasons().stream().filter(s -> seasonNumber == s.getSeasonNumber()).findFirst();
        if(!season.isPresent()) return status(Status.NOT_FOUND).build();

        List<EpisodeDTO> episodes = season.get().getEpisodes().stream()
                .map(e -> new EpisodeDTO(e, userService.getLoggedUser())).collect(Collectors.toList());

        return ok(new GenericEntity<List<EpisodeDTO>>(episodes) {}).build();
    }

    @GET
    @Path("/{seriesId}/seasons/{seasonNumber}/episodes/{episodeNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisode(@PathParam("seriesId") Long seriesId, @PathParam("seasonNumber") Integer seasonNumber, @PathParam("episodeNumber") Integer episodeNumber) {
        Optional<Series> series = seriesService.getSerieById(seriesId);
        if(!series.isPresent()) return status(Status.NOT_FOUND).build();

        Optional<Season> season = series.get().getSeasons().stream().filter(s -> seasonNumber == s.getSeasonNumber()).findFirst();
        if(!season.isPresent()) return status(Status.NOT_FOUND).build();

        Optional<Episode> episode = season.get().getEpisodes().stream().filter(e -> episodeNumber == e.getNumEpisode()).findFirst();
        if(!episode.isPresent()) return status(Status.NOT_FOUND).build();

        return ok(new EpisodeDTO(episode.get(),userService.getLoggedUser())).build();
    }

    @PUT
    @Path("/{seriesId}/seasons/{seasonNumber}/episodes/{episodeNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewEpisode(@PathParam("seriesId") Long seriesId, @PathParam("seasonNumber") Integer seasonNumber, @PathParam("episodeNumber") Integer episodeNumber,
                                @Valid ViewedResourceDTO viewedResourceDTO){

        Set<ConstraintViolation<ViewedResourceDTO>> violations = validator.validate(viewedResourceDTO);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }
        //Commented it because it's a condition already checked by the service
        /*
        if(seriesId < 0 || seasonNumber < 0 || episodeNumber < 0) {
            return status(Status.BAD_REQUEST).build();
        }
        */
        try {
            if(viewedResourceDTO.getViewedByUser()) {
                seriesService.setViewedEpisode(seriesId,seasonNumber,episodeNumber);
            } else {
                seriesService.unviewEpisode(seriesId,seasonNumber,episodeNumber);
            }
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
        return status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/watchlist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWatchlist(@QueryParam("page") Integer page) {
        try {
            page = page == null || page < 1 ? 1 : page;
            List<Episode> episodeList = seriesService.getWatchList(page);
            WatchlistDTO[] watchlist = new WatchlistDTO[episodeList.size()];
            for(int i = 0; i < episodeList.size(); i++){
                watchlist[i] = new WatchlistDTO(episodeList.get(i), uriInfo);
            }
            ResponseBuilder rb = ok(watchlist);
            boolean next = seriesService.getWatchList(page + 1).size() > 0;
            if(page > 1 || next) {
                rb.header("Link", (next ? (uriInfo.getAbsolutePathBuilder().queryParam("page", page + 1).build().toString() + " , rel = next") : "")
                        + ((next && page > 1) ? " ; " : "") + (page > 1 ? (uriInfo.getAbsolutePathBuilder().queryParam("page", page - 1).build().toString() + " , rel = prev") : ""));
            }
            return rb.build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
    }
}
