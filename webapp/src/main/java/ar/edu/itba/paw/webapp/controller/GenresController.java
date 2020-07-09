package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.webapp.dtos.GenreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

@Path("genres")
@Component
public class GenresController {

    @Autowired
    private UserService userService;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGenres() {
        List<GenreDTO> genres = seriesService.getAllGenres().stream()
                .map(g -> {
                    GenreDTO aux = new GenreDTO(g, uriInfo);
                    Object[] empty = {};
                    String name = messageSource.getMessage("genres." + g.getI18Key(), empty, LocaleContextHolder.getLocale());
                    aux.setName(name);
                    return aux;
                }).collect(Collectors.toList());
        return ok(new GenericEntity<List<GenreDTO>>(genres) {}).build();
    }

    @GET
    @Path("/{genreId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeriesInGenre(@PathParam("genreId") Long genreId, @QueryParam("page") Integer page, @QueryParam("pagesize") Integer pageSize) {
        Map<Genre, List<Series>> map;
        Integer auxPage = page == null || page <= 0 ? 1 : page;
        Integer auxPageSize = pageSize == null || pageSize <= 0 || pageSize > 21 ? 21 : pageSize;
        map = seriesService.getSeriesByGenre(genreId, Long.valueOf(auxPage), auxPageSize);
        Optional<Genre> optGenre = map.keySet().stream().findFirst();
        if(!optGenre.isPresent()) {
            return status(Response.Status.NOT_FOUND).build();
        }
        Genre g = optGenre.get();
        Response.ResponseBuilder rb = ok(new GenreDTO(g,map.get(g), userService.getLoggedUser(), uriInfo));
        if(g.isAreNext()) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", g.getPage() + 1)
                    .queryParam("pagesize", auxPageSize)
                    .build(), "next");
        }
        if(g.isArePrevious()) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", g.getPage() - 1)
                    .queryParam("pagesize", auxPageSize)
                    .build(), "prev");
        }
        return rb.build();
    }

}
