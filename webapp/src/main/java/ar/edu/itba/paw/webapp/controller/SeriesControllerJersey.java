package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.print.attribute.standard.Media;
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
    @Path("/")
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
        userService.getLoggedUser().ifPresent(user -> {
            seriesDTO.setLoggedInUserFollows(series.getUserFollowers().contains(user));
            for (Rating rating : user.getRatings()) {
                if (rating.getSeries().getId() == series.getId()) {
                    seriesDTO.setLoggedInUserRating(rating.getRating());
                    break;
                }
            }
        });
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
        return Response.ok(new SeriesReviewsDTO(series)).build();
    }

    @POST
    @Path("/{seriesId}/reviews")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReview(@PathParam("seriesId") Long seriesId, SeriesReviewDTO seriesReviewDTO) {
        try {
            Optional<SeriesReview> optSeriesReview = seriesService.addSeriesReview(seriesReviewDTO.getBody(), seriesId, seriesReviewDTO.isSpam());
            if(!optSeriesReview.isPresent()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(new SeriesReviewDTO(optSeriesReview.get())).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/{seriesId}/reviews/{seriesReviewId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(@PathParam("seriesId") Long seriesId, @PathParam("seriesReviewId") Long seriesReviewId, SeriesReviewCommentDTO seriesReviewCommentDTO) {
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
