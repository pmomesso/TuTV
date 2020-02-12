package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static javax.ws.rs.core.Response.*;

@Path("series")
public class SeriesControllerJersey {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private SeriesService seriesService;
    @Autowired
    private UserService userService;

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
    @Path("/{seriesId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisode(@PathParam("seriesId") Long seriesId) {
        Optional<Series> optSeries = seriesService.getSerieById(seriesId);
        if (!optSeries.isPresent()) {
            return status(Status.NOT_FOUND).build();
        }
        Series series = optSeries.get();
        SeriesDTO seriesDTO = new SeriesDTO(series);
        seriesDTO.setSeasonsList(series, userService.getLoggedUser());
        seriesDTO.setUserFields(userService.getLoggedUser(), series);
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
    public Response createReview(@PathParam("seriesId") Long seriesId, @Valid SeriesReviewDTO seriesReviewDTO) {
        try {
            Optional<SeriesReview> optSeriesReview = seriesService.addSeriesReview(seriesReviewDTO.getBody(), seriesId, seriesReviewDTO.getSpam());
            if(!optSeriesReview.isPresent()) {
                return status(Status.NOT_FOUND).build();
            }
            return ok(new SeriesReviewDTO(optSeriesReview.get())).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/reviews/{seriesReviewId}/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(@PathParam("seriesReviewId") Long seriesReviewId, SeriesReviewCommentDTO seriesReviewCommentDTO) {
        //Todo: fix baseUrl
        try {
            SeriesReviewComment seriesReviewComment = seriesService.addCommentToPost(seriesReviewId, seriesReviewCommentDTO.getBody(), null);
            return ok(new SeriesReviewCommentDTO(seriesReviewComment)).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        }
    }

    @PUT
    @Path("/reviews")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSeriesReview(SeriesReviewDTO seriesReviewDTO) {
        if(seriesReviewDTO.getLoggedInUserLikes() == null) {
            return status(Status.BAD_REQUEST).build();
        }
        try {
            if(seriesReviewDTO.getLoggedInUserLikes()) {
                seriesService.likePost(seriesReviewDTO.getId());
            } else {
                seriesService.unlikePost(seriesReviewDTO.getId());
            }
            return accepted().entity(seriesService.getSeriesReviewById(seriesReviewDTO.getId()).get()).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/reviews/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSeriesReviewComment(SeriesReviewCommentDTO seriesReviewCommentDTO) {
        if(seriesReviewCommentDTO.getLoggedInUserLikes() == null) {
            return status(Status.BAD_REQUEST).build();
        }
        try {
            if (seriesReviewCommentDTO.getLoggedInUserLikes()) {
                seriesService.likeComment(seriesReviewCommentDTO.getId());
            } else {
                seriesService.unlikeComment(seriesReviewCommentDTO.getId());
            }
            return accepted().build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSeries(SeriesDTO seriesDTO) {
        if(seriesDTO.getId() == null || (seriesDTO.getLoggedInUserRating() == null && seriesDTO.isLoggedInUserFollows() == null)) {
            return status(Status.BAD_REQUEST).build();
        }
        try {
            if(seriesDTO.isLoggedInUserFollows()) {
                seriesService.followSeries(seriesDTO.getId());
            } else {
                seriesService.unfollowSeries(seriesDTO.getId());
            }
            if(seriesDTO.getLoggedInUserRating() != null) {
                seriesService.rateSeries(seriesDTO.getId(), seriesDTO.getLoggedInUserRating());
            }
            return accepted().entity(new SeriesDTO(seriesService.getSerieById(seriesDTO.getId()).get())).build();
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
            map = seriesService.getSeriesByGenre(genreId, Long.valueOf(1));
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

}
