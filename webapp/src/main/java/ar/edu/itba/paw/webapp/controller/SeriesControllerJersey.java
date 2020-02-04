package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.webapp.dtos.MainPageDTO;
import ar.edu.itba.paw.webapp.dtos.SeriesDTO;
import ar.edu.itba.paw.webapp.dtos.SeriesReviewCommentDTO;
import ar.edu.itba.paw.webapp.dtos.SeriesReviewsDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("series")
public class SeriesControllerJersey {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private SeriesService seriesService;

    @GET
    @Path("/{seriesId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisode(@PathParam("seriesId") Long seriesId) {
        try {
            Series series = seriesService.getSerieById(seriesId).orElseThrow(NotFoundException::new);
            SeriesDTO seriesDTO = new SeriesDTO(series);
            seriesDTO.setSeasonsList(series);
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
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMainPageJson() {
        //Todo: fix paging
        return Response.ok(new MainPageDTO(seriesService.getNewestSeries(0, 4), seriesService.getSeriesByGenre())).build();
    }

}
