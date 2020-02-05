package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Rating;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.webapp.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("series")
public class SeriesControllerJersey {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private SeriesService seriesService;
    @Autowired
    private UserService userService;

    @GET
    @Path("/{seriesId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisode(@PathParam("seriesId") Long seriesId) {
        try {
            Series series = seriesService.getSerieById(seriesId).orElseThrow(NotFoundException::new);
            SeriesDTO seriesDTO = new SeriesDTO(series);
            seriesDTO.setSeasonsList(series);
            userService.getLoggedUser().ifPresent(user -> {
                seriesDTO.setLoggedInUserFollows(series.getUserFollowers().contains(user));
                for(Rating rating : user.getRatings()) {
                    if(rating.getSeries().getId() == series.getId()) {
                        seriesDTO.setLoggedInUserRating(rating.getRating());
                        break;
                    }
                }
            });
            return Response.ok(seriesDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{seriesId}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeriesComments(@PathParam("seriesId") Long seriesId) {
        try {
            return Response.ok(new SeriesReviewsDTO(seriesService.getSerieById(seriesId).orElseThrow(NotFoundException::new))).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/genres/{genreId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeriesInGenre(@PathParam("genreId") Long genreId) {
        try {
            Genre genre = seriesService.getGenreById(genreId);
            List<Series> series = seriesService.getAllSeriesByGenre(genreId);
            return Response.ok(new GenreDTO(genre, series)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMainPageJson() {
        //Todo: fix paging
        return Response.ok(new MainPageDTO(seriesService.getNewestSeries(0, 4), seriesService.getSeriesByGenre())).build();
    }

}
