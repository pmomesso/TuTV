package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
        //Todo: fix paging
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);
        ResponseBuilder builder = Response.ok(new MainPageDTO(seriesService.getNewestSeries(0, 4)));
        builder.cacheControl(cc);
        return builder.build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSeries(@QueryParam("name") String name, @QueryParam("genre") String genre, @QueryParam("network") String network, @QueryParam("page") Integer page) {

        page = page == null || page < 1 ? 1 : page;
        List<Series> results = seriesService.searchSeries(name,genre,network,page - 1);
        SeriesDTO[] seriesDTOList = new SeriesDTO[results.size()];
        for(int i = 0; i < results.size(); i++){
            seriesDTOList[i] = new SeriesDTO(results.get(i));
        }
        ResponseBuilder rb = ok(seriesDTOList);
        boolean next = seriesService.searchSeries(name,genre,network, page + 1).size() > 0;
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
        seriesDTO.setSeasonsList(series, userService.getLoggedUser());
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
        GenreListDTO genreListDTO = new GenreListDTO(seriesService.getAllGenres());
        genreListDTO.getGenres().stream().forEach(genreDTO -> genreDTO.setSeries(null));
        ResponseBuilder rb = ok(genreListDTO);
        return rb.build();
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
        ResponseBuilder rb = ok(new GenreDTO(g, map.get(g)));
        if(g.isAreNext() || g.isArePrevious()) {
            rb.header("Link", (g.isAreNext() ? (uriInfo.getAbsolutePathBuilder().queryParam("page", g.getPage() + 1).build().toString() + " , rel = next") : "")
                    + ((g.isAreNext() && g.isArePrevious()) ? " ; " : "") + (g.isArePrevious() ? (uriInfo.getAbsolutePathBuilder().queryParam("page", g.getPage() - 1).build().toString() + " , rel = prev") : ""));
        }
        return rb.build();
    }

    @PUT
    @Path("/{seriesId}/seasons/{seasonId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewSeason(@PathParam("seriesId") Long seriesId, @PathParam("seasonId") Long seasonId,
                               @Valid ViewedResourceDTO viewedResourceDTO){

        Set<ConstraintViolation<ViewedResourceDTO>> violations = validator.validate(viewedResourceDTO);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }

        if(seriesId < 0 || seasonId < 0){
            return status(Status.BAD_REQUEST).build();
        }

        try {
            if(viewedResourceDTO.getViewedByUser()){
                seriesService.setViewedSeason(seriesId,seasonId);
            }
            else{
                seriesService.unviewSeason(seasonId);
            }
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }

        return status(Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/{seriesId}/seasons/{seasonId}/episodes/{episodeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewEpisode(@PathParam("seriesId") Long seriesId, @PathParam("seasonId") Long seasonId, @PathParam("episodeId") Long episodeId,
                                @Valid ViewedResourceDTO viewedResourceDTO){

        Set<ConstraintViolation<ViewedResourceDTO>> violations = validator.validate(viewedResourceDTO);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }

        if(seriesId < 0 || seasonId < 0 || episodeId < 0) {
            return status(Status.BAD_REQUEST).build();
        }
        try {
            if(viewedResourceDTO.getViewedByUser()) {
                seriesService.setViewedEpisode(seriesId,episodeId);
            } else {
                seriesService.unviewEpisode(episodeId);
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
    public Response getWatchlist() {
        try {
            List<Episode> episodeList = seriesService.getWatchList();
            WatchlistDTO[] watchlist = new WatchlistDTO[episodeList.size()];
            for(int i = 0; i < episodeList.size(); i++){
                watchlist[i] = new WatchlistDTO(episodeList.get(i));
            }
            return ok(watchlist).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
    }
}
