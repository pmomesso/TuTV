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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("series")
public class SeriesControllerJersey {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private SeriesService seriesService;
    @Autowired
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMainPageJson() {
        //Todo: fix paging
        return Response.ok(new MainPageDTO(seriesService.getNewestSeries(0, 4), seriesService.getSeriesByGenre())).build();
    }

    @GET
    @Path("/{seriesId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisode(@PathParam("seriesId") Long seriesId) {
        Optional<Series> optSeries = seriesService.getSerieById(seriesId);
        if (!optSeries.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Series series = optSeries.get();
        SeriesDTO seriesDTO = new SeriesDTO(series);
        seriesDTO.setSeasonsList(series);
        Optional<User> loggedUser = userService.getLoggedUser();
        if(loggedUser.isPresent()) {
                seriesDTO.setLoggedInUserFollows(series.getUserFollowers().contains(loggedUser.get()));
                for (Rating rating : loggedUser.get().getRatings()) {
                    if (rating.getSeries().getId() == series.getId()) {
                        seriesDTO.setLoggedInUserRating(rating.getRating());
                        break;
                    }
                }
        }
        return Response.ok(seriesDTO).build();
    }

    @GET
    @Path("/{seriesId}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeriesComments(@PathParam("seriesId") Long seriesId) {
        Optional<Series> optSeries = seriesService.getSerieById(seriesId);
        if(!optSeries.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Series series = optSeries.get();
        SeriesReviewsDTO seriesReviewsDTO = new SeriesReviewsDTO(series);
        if(userService.getLoggedUser().isPresent()) {
            for(SeriesReviewDTO seriesReviewDTO : seriesReviewsDTO.getSeriesReviews()) {
                seriesReviewDTO.setLoggedInUserLikes(seriesService.getLoggedInUserLikesSeriesReview(seriesReviewDTO.getId()).get());
            }
        }
        return Response.ok(seriesReviewsDTO).build();
    }

    @POST
    @Path("/{seriesId}/reviews")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReview(@PathParam("seriesId") Long seriesId, @Valid SeriesReviewDTO seriesReviewDTO) {
        try {
            Optional<SeriesReview> optSeriesReview = seriesService.addSeriesReview(seriesReviewDTO.getBody(), seriesId, seriesReviewDTO.getSpam());
            if(!optSeriesReview.isPresent()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(new SeriesReviewDTO(optSeriesReview.get())).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
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
            return Response.ok(new SeriesReviewCommentDTO(seriesReviewComment)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @PUT
    @Path("/reviews")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSeriesReview(SeriesReviewDTO seriesReviewDTO) {
        if(seriesReviewDTO.getLoggedInUserLikes() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            if(seriesReviewDTO.getLoggedInUserLikes()) {
                seriesService.likePost(seriesReviewDTO.getId());
            } else {
                seriesService.unlikePost(seriesReviewDTO.getId());
            }
            return Response.accepted().entity(seriesService.getSeriesReviewById(seriesReviewDTO.getId()).get()).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/reviews/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSeriesReviewComment(SeriesReviewCommentDTO seriesReviewCommentDTO) {
        if(seriesReviewCommentDTO.getLoggedInUserLikes() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            if (seriesReviewCommentDTO.getLoggedInUserLikes()) {
                seriesService.likeComment(seriesReviewCommentDTO.getId());
            } else {
                seriesService.unlikeComment(seriesReviewCommentDTO.getId());
            }
            return Response.accepted().build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSeries(SeriesDTO seriesDTO) {
        if(seriesDTO.getId() == null || (seriesDTO.getLoggedInUserRating() == null && seriesDTO.isLoggedInUserFollows() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
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
            return Response.accepted().entity(new SeriesDTO(seriesService.getSerieById(seriesDTO.getId()).get())).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/genres/{genreId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeriesInGenre(@PathParam("genreId") Long genreId, @QueryParam("page") Integer page) {
        Map<Genre, List<Series>> map = seriesService.getSeriesByGenre(genreId, Long.valueOf(page));
        Optional<Genre> optGenre = map.keySet().stream().findFirst();
        if(!optGenre.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new GenreDTO(optGenre.get(), map.get(optGenre.get()))).build();
    }

}
