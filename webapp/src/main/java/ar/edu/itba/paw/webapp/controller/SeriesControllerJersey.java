package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.*;

@Path("api/series")
@Component
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
    public Response getFeatured() {
        List<SeriesDTO> featured = seriesService.getNewestSeries(0, 4).stream()
                .map(e -> new SeriesDTO(e, userService.getLoggedUser(), uriInfo)).collect(Collectors.toList());
        return ok(new GenericEntity<List<SeriesDTO>>(featured) {}).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSeries(@QueryParam("name") String name, @QueryParam("genre") Long genre, @QueryParam("network") Long network,
                                 @QueryParam("page") Integer page, @QueryParam("pagesize") Integer pageSize) {

        page = page == null || page < 1 ? 1 : page;
        pageSize = pageSize == null || pageSize <= 0 || pageSize >= 24 ? 24 : pageSize;
        List<Series> results = seriesService.searchSeries(name,genre,network,page, pageSize);
        SeriesDTO[] seriesDTOList = new SeriesDTO[results.size()];
        for(int i = 0; i < results.size(); i++){
            seriesDTOList[i] = new SeriesDTO(results.get(i), userService.getLoggedUser(), uriInfo);
        }
        ResponseBuilder rb = ok(seriesDTOList);
        boolean next = seriesService.searchSeries(name,genre,network,page + 1, pageSize).size() > 0;
        if(next || page > 1) {
            UriBuilder nextUri = uriInfo.getAbsolutePathBuilder();
            UriBuilder prevUri = uriInfo.getAbsolutePathBuilder();
            if(name != null && name.length() > 0){
                nextUri.queryParam("name", name);
                prevUri.queryParam("name", name);
            }
            if(genre != null){
                nextUri.queryParam("genre", genre);
                prevUri.queryParam("genre", genre);
            }
            if(network != null){
                nextUri.queryParam("network", network);
                prevUri.queryParam("network", network);
            }
            if(next) {
                rb = rb.link(prevUri
                        .queryParam("page", page + 1)
                        .queryParam("pagesize", pageSize)
                        .build(), "next");
            }
            if(page > 1) {
                int size = seriesService.searchSeries(name,genre,network,page - 1, pageSize).size();
                if(size > 0) {
                    rb = rb.link(nextUri
                            .queryParam("page", page - 1)
                            .queryParam("pagesize", pageSize)
                            .build(), "prev");
                }
            }
        }
        return rb.build();
    }

    @GET
    @Path("/{seriesId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisode(@PathParam("seriesId") Long seriesId) throws NotFoundException {
        Optional<Series> optSeries = seriesService.getSerieById(seriesId);
        if (!optSeries.isPresent()) {
            throw new NotFoundException();
        }
        Series series = optSeries.get();
        SeriesDTO seriesDTO = new SeriesDTO(series, userService.getLoggedUser(), uriInfo);
        seriesDTO.setSeasonFields(series, userService.getLoggedUser());
        return ok(seriesDTO).build();
    }

    @GET
    @Path("/{seriesId}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeriesComments(@PathParam("seriesId") Long seriesId) throws NotFoundException {
        List<SeriesReviewDTO> reviews;
        reviews = seriesService.getSeriesReviewList(seriesId).stream()
                .map(sr -> new SeriesReviewDTO(sr, userService.getLoggedUser())).collect(Collectors.toList());
        return ok(new GenericEntity<List<SeriesReviewDTO>>(reviews) {}).build();
    }

    @POST
    @Path("/{seriesId}/reviews")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReview(@PathParam("seriesId") Long seriesId,
                                 @Valid SeriesReviewNewDTO seriesReviewNewDTO) throws UnauthorizedException, BadRequestException {

        Set<ConstraintViolation<SeriesReviewNewDTO>> violations = validator.validate(seriesReviewNewDTO);
        if(!violations.isEmpty()) {
            throw new BadRequestException();
        }
        Optional<SeriesReview> optSeriesReview = seriesService.addSeriesReview(seriesReviewNewDTO.getBody(), seriesId,
                seriesReviewNewDTO.getIsSpam());
        if(!optSeriesReview.isPresent()) {
            return status(Status.NOT_FOUND).build();
        }
        return ok(new SeriesReviewDTO(optSeriesReview.get(), userService.getLoggedUser())).build();
    }

    @POST
    @Path("/{seriesId}/reviews/{seriesReviewId}/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(@PathParam("seriesId") Long seriesId, @PathParam("seriesReviewId") Long seriesReviewId,
                                  @Valid SeriesReviewCommentNewDTO seriesReviewCommentNewDTO) throws NotFoundException, UnauthorizedException, BadRequestException {

        Set<ConstraintViolation<SeriesReviewCommentNewDTO>> violations = validator.validate(seriesReviewCommentNewDTO);
        if(!violations.isEmpty()) {
            throw new BadRequestException();
        }

        Optional<Series> series = seriesService.serieWithReview(seriesReviewId);
        if(!series.isPresent() || series.get().getId() != seriesId) {
            throw new BadRequestException();
        }

        //Todo: fix baseUrl
        SeriesReviewComment seriesReviewComment = seriesService.addCommentToPost(seriesReviewId, seriesReviewCommentNewDTO.getBody(), null);
        return ok(new SeriesReviewCommentDTO(seriesReviewComment, userService.getLoggedUser())).build();
    }

    @PUT
    @Path("/{seriesId}/reviews/{seriesReviewId}/like")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSeriesReview(@PathParam("seriesId") Long seriesId, @PathParam("seriesReviewId") Long seriesReviewId,
                                       @Valid SeriesReviewStateDTO seriesReviewStateDTO) throws NotFoundException, UnauthorizedException, BadRequestException {
        Set<ConstraintViolation<SeriesReviewStateDTO>> violations = validator.validate(seriesReviewStateDTO);
        if(!violations.isEmpty()) {
            throw new BadRequestException();
        }
        Optional<Series> series = seriesService.getSerieById(seriesId);
        if(!series.isPresent() || series.get().getSeriesReviewList().stream().filter(r -> r.getId() == seriesReviewId).count() == 0) {
            throw new NotFoundException();
        }
        SeriesReview seriesReview;
        if(seriesReviewStateDTO.getLoggedInUserLikes()) {
            seriesReview = seriesService.likePost(seriesReviewId);
        } else {
            seriesReview = seriesService.unlikePost(seriesReviewId);
        }
        return ok(new NumLikesDTO(seriesReview.getNumLikes(), seriesReviewStateDTO.getLoggedInUserLikes())).build();
    }

    @PUT
    @Path("/{seriesId}/reviews/{seriesReviewId}/comments/{commentId}/like")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSeriesReviewComment(@PathParam("seriesId") Long seriesId, @PathParam("seriesReviewId") Long seriesReviewId,
            @PathParam("commentId") Long commentId, @Valid SeriesReviewCommentStateDTO seriesReviewCommentStateDTO) throws NotFoundException, UnauthorizedException, BadRequestException {

        Set<ConstraintViolation<SeriesReviewCommentStateDTO>> violations = validator.validate(seriesReviewCommentStateDTO);
        if(!violations.isEmpty()) {
            throw new BadRequestException();
        }

        Optional<Series> optSeries = seriesService.getSerieById(seriesId);
        if(!optSeries.isPresent()) {
            throw new NotFoundException();
        }
        Optional<SeriesReview> optReview = optSeries.get().getSeriesReviewList().stream().filter(r -> r.getId() == seriesReviewId).findFirst();
        if(!optReview.isPresent() || optReview.get().getComments().stream().filter(c -> c.getId() == commentId).count() == 0) {
            throw new NotFoundException();
        }

        SeriesReviewComment seriesReviewComment;
        if (seriesReviewCommentStateDTO.getLoggedInUserLikes()) {
            seriesReviewComment = seriesService.likeComment(commentId);
        } else {
            seriesReviewComment = seriesService.unlikeComment(commentId);
        }
        return ok(new NumLikesDTO(seriesReviewComment.getNumLikes(), seriesReviewCommentStateDTO.getLoggedInUserLikes())).build();
    }

    @DELETE
    @Path("/{seriesId}/reviews/{reviewId}")
    public Response deletePost(@PathParam("seriesId") Long seriesId, @PathParam("reviewId") Long reviewId) throws UnauthorizedException, NotFoundException {
        Optional<Series> optSeries = seriesService.getSerieById(seriesId);
        if(!optSeries.isPresent() || optSeries.get().getSeriesReviewList().stream().filter(r -> r.getId() == reviewId).count() == 0) {
            throw new NotFoundException();
        }
        seriesService.removePost(reviewId);
        return ok().build();
    }

    @DELETE
    @Path("/{seriesId}/reviews/{reviewId}/comments/{commentId}")
    public Response deleteComment(@PathParam("seriesId") Long seriesId, @PathParam("reviewId") Long reviewId, @PathParam("commentId") Long commentId) throws UnauthorizedException, NotFoundException {
        Optional<Series> optSeries = seriesService.getSerieById(seriesId);
        if(!optSeries.isPresent()) throw new NotFoundException();
        Optional<SeriesReview> optReview = optSeries.get().getSeriesReviewList().stream().filter(r -> r.getId() == reviewId).findFirst();
        if(!optReview.isPresent()) throw new NotFoundException();
        Optional<SeriesReviewComment> optComment = optReview.get().getComments().stream().filter(c -> c.getId() == commentId).findFirst();
        if(!optComment.isPresent()) throw new NotFoundException();

        seriesService.removeComment(commentId);

        return ok().build();
    }

    @PUT
    @Path("/{seriesId}/rating")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSeries(@PathParam("seriesId") Long seriesId, @Valid SerieStateDTO serieStateDTO) throws UnauthorizedException, BadRequestException, NotFoundException {

        Set<ConstraintViolation<SerieStateDTO>> violations = validator.validate(serieStateDTO);
        if(!violations.isEmpty()) throw new BadRequestException();

        Series series = seriesService.rateSeries(seriesId, serieStateDTO.getLoggedInUserRating());
        return ok(new RatingDTO(series.getUserRating(), serieStateDTO.getLoggedInUserRating())).build();
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
    public Response getSeriesSeason(@PathParam("seriesId") Long seriesId, @PathParam("seasonNumber") Integer seasonNumber) throws NotFoundException {
        Optional<User> loggedUser = userService.getLoggedUser();
        List<Season> seasons = seriesService.getSeasonsBySeriesId(seriesId);
        Optional<Season> season = seasons.stream().filter(s -> seasonNumber == s.getSeasonNumber()).findFirst();
        if(!season.isPresent()) throw new NotFoundException();
        SeasonDTO seasonDTO = new SeasonDTO(season.get(), loggedUser);
        return ok(seasonDTO).build();
    }

    @PUT
    @Path("/{seriesId}/seasons/{seasonNumber}/viewed")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewSeason(@PathParam("seriesId") Long seriesId, @PathParam("seasonNumber") Integer seasonNumber,
                               @Valid ViewedResourceDTO viewedResourceDTO, @QueryParam("markPrevious") Boolean markPrevious) throws UnauthorizedException, NotFoundException, BadRequestException {

        Set<ConstraintViolation<ViewedResourceDTO>> violations = validator.validate(viewedResourceDTO);
        if(!violations.isEmpty()) throw new BadRequestException();

        if(seriesId < 0 || seasonNumber < 0) throw new NotFoundException();
        if(markPrevious != null && !viewedResourceDTO.getViewedByUser()) throw new BadRequestException();

        if(viewedResourceDTO.getViewedByUser()){
            if(markPrevious != null && markPrevious) {
                seriesService.viewUntilSeason(seriesId, seasonNumber);
            } else {
                seriesService.setViewedSeason(seriesId,seasonNumber);
            }
        }
        else{
            seriesService.unviewSeason(seriesId,seasonNumber);
        }

        return ok(viewedResourceDTO).build();
    }

    @GET
    @Path("/{seriesId}/seasons/{seasonNumber}/episodes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeasonEpisodes(@PathParam("seriesId") Long seriesId, @PathParam("seasonNumber") Integer seasonNumber) throws NotFoundException {
        Optional<Series> series = seriesService.getSerieById(seriesId);
        if(!series.isPresent()) throw new NotFoundException();

        Optional<Season> season = series.get().getSeasons().stream().filter(s -> seasonNumber == s.getSeasonNumber()).findFirst();
        if(!season.isPresent()) throw new NotFoundException();

        List<EpisodeDTO> episodes = season.get().getEpisodes().stream()
                .map(e -> new EpisodeDTO(e, userService.getLoggedUser())).collect(Collectors.toList());

        return ok(new GenericEntity<List<EpisodeDTO>>(episodes) {}).build();
    }

    @GET
    @Path("/{seriesId}/seasons/{seasonNumber}/episodes/{episodeNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisode(@PathParam("seriesId") Long seriesId, @PathParam("seasonNumber") Integer seasonNumber, @PathParam("episodeNumber") Integer episodeNumber) throws NotFoundException {
        Optional<Series> series = seriesService.getSerieById(seriesId);
        if(!series.isPresent()) throw new NotFoundException();

        Optional<Season> season = series.get().getSeasons().stream().filter(s -> seasonNumber == s.getSeasonNumber()).findFirst();
        if(!season.isPresent()) throw new NotFoundException();

        Optional<Episode> episode = season.get().getEpisodes().stream().filter(e -> episodeNumber == e.getNumEpisode()).findFirst();
        if(!episode.isPresent()) throw new NotFoundException();

        return ok(new EpisodeDTO(episode.get(),userService.getLoggedUser())).build();
    }

    @PUT
    @Path("/{seriesId}/seasons/{seasonNumber}/episodes/{episodeNumber}/viewed")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewEpisode(@PathParam("seriesId") Long seriesId, @PathParam("seasonNumber") Integer seasonNumber, @PathParam("episodeNumber") Integer episodeNumber,
                                @Valid ViewedResourceDTO viewedResourceDTO, @QueryParam("markPrevious") Boolean markPrevious) throws NotFoundException, UnauthorizedException, BadRequestException {

        Set<ConstraintViolation<ViewedResourceDTO>> violations = validator.validate(viewedResourceDTO);
        if(!violations.isEmpty()) throw new BadRequestException();
        if(markPrevious != null && !viewedResourceDTO.getViewedByUser()) throw new BadRequestException();

        if(viewedResourceDTO.getViewedByUser()) {
            if(markPrevious != null && markPrevious) {
                seriesService.viewUntilEpisode(seriesId, seasonNumber, episodeNumber);
            } else {
                seriesService.setViewedEpisode(seriesId,seasonNumber,episodeNumber);
            }
        } else {
            seriesService.unviewEpisode(seriesId,seasonNumber,episodeNumber);
        }
        return ok(viewedResourceDTO).build();
    }

}
