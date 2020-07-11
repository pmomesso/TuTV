package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.dtos.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.*;

@Path("api/users")
@Component
public class UserControllerJersey {


    @Context
    UriInfo uriInfo;

    @Autowired
    private UserService userService;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private Validator validator;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response register(@Valid RegisterDTO register) throws BadRequestException, UnauthorizedException {

        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(register);
        if(!violations.isEmpty()) {
            throw new BadRequestException();
        }
        final URI baseUri = uriInfo.getBaseUri();
        Either<User, Collection<Errors>> u;
        u = userService.createUser(register.getUsername(),register.getPassword(),register.getMail(),false,baseUri.toString());
        if(u.isValuePresent()){
            return accepted().entity(new UserDTO(u.getValue())).build();
        }
        else{
            ErrorDTO[] errors = new ErrorDTO[u.getAlternative().size()];
            if(u.getAlternative().contains(Errors.MAIL_ALREADY_IN_USE)){
                errors[0] = new ErrorDTO("mail","mailExists");
            }
            if(u.getAlternative().contains(Errors.USERNAME_ALREADY_IN_USE)){
                int index = errors[0] == null ? 0 : 1;
                errors[index] = new ErrorDTO("username","usernameExists");
            }
            return status(Status.CONFLICT).entity(errors.length > 1 ? errors : errors[0]).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/recentlyWatched")
    public Response getRecentlyWatched(@PathParam("userId") Long userId) throws NotFoundException, BadRequestException {
        List series;
        series = seriesService.getRecentlyWatchedList(userId,5).stream()
                .map(e -> new SeriesDTO(e,userService.getLoggedUser(), uriInfo)).collect(Collectors.toList());
        return ok(new GenericEntity<List<SeriesDTO>>(series) {}).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/following")
    public Response getFollowedSeries(@PathParam("userId") Long userId,@QueryParam("page") Integer page, @QueryParam("pagesize") Integer pagesize) throws NotFoundException {
        page = page == null || page < 1 ? 1 : page;
        pagesize = pagesize == null || pagesize <= 0 || pagesize >= 24 ? 24 : pagesize;
        List<SeriesDTO> seriesList = seriesService.getAddedSeries(userId,page,pagesize).stream().
                map(series -> new SeriesDTO(series, userService.getLoggedUser(), uriInfo)).collect(Collectors.toList());
        ResponseBuilder rb = ok(new GenericEntity<List<SeriesDTO>>(seriesList) {});
        if(seriesService.getAddedSeries(userId,page + 1,pagesize).size() > 0) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", page + 1)
                    .queryParam("pagesize", pagesize)
                    .build(), "next");
        }
        if(page > 1) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", page - 1)
                    .queryParam("pagesize", pagesize)
                    .build(), "prev");
        }
        return rb.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{userId}/following")
    public Response followSeries(@PathParam("userId") Long userId, @Valid FollowSeriesDTO followSeriesDTO) throws UnauthorizedException, NotFoundException {
        Set<ConstraintViolation<FollowSeriesDTO>> violations = validator.validate(followSeriesDTO);
        if(!violations.isEmpty()) return Response.status(Status.BAD_REQUEST).build();
        Series series = seriesService.followSeries(followSeriesDTO.getSeriesId());
        return ok(new FollowersDTO(series.getFollowers(), true)).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{userId}/following/{seriesId}")
    public Response unfollowSeries(@PathParam("userId") Long userId, @PathParam("seriesId") Long seriesId) throws NotFoundException, UnauthorizedException {
        Series series = seriesService.unfollowSeries(seriesId);
        return ok(new FollowersDTO(series.getFollowers(), false)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    public Response getUserById(@PathParam("userId") Long userId) throws NotFoundException {
        Optional<User> optUser = userService.findById(userId);
        if(!optUser.isPresent()) {
            throw new NotFoundException();
        }
        UserDTO userDTO = new UserDTO(optUser.get());
        return ok(userDTO).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/notifications")
    public Response getUserNotifications(@PathParam("userId") Long userId) throws UnauthorizedException {
        Optional<User> loggedInUser = userService.getLoggedUser();
        if(!loggedInUser.isPresent() || loggedInUser.get().getId() != userId) {
            throw new UnauthorizedException();
        }
        List<NotificationDTO> notifications = loggedInUser.get().getNotifications().stream()
                .map(NotificationDTO::new).collect(Collectors.toList());
        return ok(new GenericEntity<List<NotificationDTO>>(notifications) {}).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/notifications/{notificationId}/viewed")
    public Response editUserNotifications(@PathParam("userId") Long userId,@PathParam("notificationId") Long notificationId, @Valid NotificationStateDTO notificationState) throws BadRequestException, UnauthorizedException, NotFoundException {
        Set<ConstraintViolation<NotificationStateDTO>> constraintViolationSet = validator.validate(notificationState);
        if(!constraintViolationSet.isEmpty()) {
            throw new BadRequestException();
        }
        if(!userService.getLoggedUser().isPresent() || userService.getLoggedUser().get().getId() != userId) {
            throw new UnauthorizedException();
        }
        User loggedUser = userService.getLoggedUser().get();
        userService.setNotificationViewed(notificationId,notificationState.getViewedByUser());
        Notification notification = new Notification();
        for(Notification n : loggedUser.getNotifications()){
            if(n.getId() == notificationId){
                notification = n;
                break;
            }
        }
        return accepted(new NotificationDTO(notification)).build();
    }

    @DELETE
    @Path("/{userId}/notifications/{notificationId}")
    public Response deleteNotification(@PathParam("userId") Long userId,@PathParam("notificationId") Long notificationId) throws UnauthorizedException, NotFoundException {
        if(!userService.getLoggedUser().isPresent() || userService.getLoggedUser().get().getId() != userId) {
            throw new UnauthorizedException();
        }
        userService.deleteNotification(userId,notificationId);
        return status(Status.NO_CONTENT).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getUsersList(@QueryParam("page") Integer page, @QueryParam("pagesize") Integer pageSize) throws UnauthorizedException {
        UsersList usersList;
        Integer auxPage = page == null ? 1 : page;
        Integer auxPageSize = pageSize == null ? 8 : pageSize;
        usersList = userService.getAllUsersExceptLoggedOne(auxPage, auxPageSize);
        List<UserDTO> users = usersList.getUsersList().stream()
                .map(u -> new UserDTO(u)).collect(Collectors.toList());
        ResponseBuilder rb = Response.ok(new GenericEntity<List<UserDTO>>(users) {});
        if(usersList.isAreNext()) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder()
                    .queryParam("page",auxPage + 1)
                    .queryParam("pagesize", auxPageSize)
                    .build(), "next");
        }
        if(usersList.isArePrevious()) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", auxPage - 1)
                    .queryParam("pagesize", auxPageSize)
                    .build(), "prev");
        }
        return rb.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/watchlist")
    public Response getUserWatchList(@PathParam("userId") Long userId, @QueryParam("page") Integer page, @QueryParam("pagesize") Integer pageSize) throws UnauthorizedException, NotFoundException {
        Optional<User> optUser = userService.getLoggedUser();
        if(optUser.isPresent() && optUser.get().getId() != userId) throw new UnauthorizedException();

        Integer auxPage = page;
        if(page == null) {
            auxPage = 1;
        }
        Integer auxPageSize = pageSize;
        if(pageSize == null) {
            auxPageSize = 24;
        }
        List<ToBeSeenEpisodeDTO> episodes;
        ResponseBuilder rb = ok();
        episodes = seriesService.getWatchList(auxPage, auxPageSize).stream()
                .map(e -> new ToBeSeenEpisodeDTO(new SeriesDTO(e.getSeason().getSeries(), optUser, uriInfo),
                        new EpisodeDTO(e, optUser))).collect(Collectors.toList());
        rb = rb.entity(new GenericEntity<List<ToBeSeenEpisodeDTO>>(episodes) {});
        if(auxPage > 1 && episodes.size() > 0) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", auxPage - 1)
                    .queryParam("pagesize", auxPageSize)
                    .build(), "prev");
        }
        int size = seriesService.getWatchList(auxPage + 1, auxPageSize).size();
        if(size > 0) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", auxPage + 1)
                    .queryParam("pagesize", auxPageSize)
                    .build(), "next");
        }

        return rb.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/upcoming")
    public Response getUserUpcomingList(@PathParam("userId") Long userId, @QueryParam("page") Integer page, @QueryParam("pagesize") Integer pagesize) throws UnauthorizedException, NotFoundException {
        page = page == null || page < 1 ? 1 : page;
        pagesize = pagesize == null || pagesize <= 0 || pagesize >= 24 ? 24 : pagesize;
        Optional<User> optUser = userService.getLoggedUser();
        if(optUser.isPresent() && optUser.get().getId() != userId) throw new UnauthorizedException();
        List<ToBeSeenEpisodeDTO> episodes = seriesService.getUpcomingEpisodes(page,pagesize).stream()
                .map(e -> new ToBeSeenEpisodeDTO(new SeriesDTO(e.getSeason().getSeries(), optUser, uriInfo),
                        new EpisodeDTO(e, optUser))).collect(Collectors.toList());
        ResponseBuilder rb = ok(new GenericEntity<List<ToBeSeenEpisodeDTO>>(episodes) {});
        if(seriesService.getUpcomingEpisodes(page + 1,pagesize).size() > 0) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", page + 1)
                    .queryParam("pagesize", pagesize)
                    .build(), "next");
        }
        if(page > 1) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder()
                    .queryParam("page", page - 1)
                    .queryParam("pagesize", pagesize)
                    .build(), "prev");
        }
        return rb.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/lists")
    public Response getUserFavourites(@PathParam("userId") Long userId) throws UnauthorizedException {
        Optional<User> optUser = userService.getLoggedUser();
        if(!optUser.isPresent() || optUser.get().getId() != userId) {
            throw new UnauthorizedException();
        }
        User user = optUser.get();
        List<SeriesListDTO> seriesLists = user.getLists().stream()
                .map(list -> new SeriesListDTO(list, uriInfo)).collect(Collectors.toList());
        return ok(new GenericEntity<List<SeriesListDTO>>(seriesLists) {}).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/lists/{listId}/series")
    public Response getUserList(@PathParam("userId") Long userId, @PathParam("listId") Long listId,
            @QueryParam("page") Integer page, @QueryParam("pagesize") Integer pageSize) throws NotFoundException {

        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 24 : pageSize;

        List<SeriesDTO> series;
        ResponseBuilder rb = ok();
        series = seriesService.getSeriesInList(listId, page, pageSize)
                .stream().map(s -> new SeriesDTO(s, userService.getLoggedUser(), uriInfo)).collect(Collectors.toList());
        List<Series> aux = seriesService.getSeriesInList(listId, page + 1, pageSize);
        if(!aux.isEmpty()) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page + 1).queryParam("pagesize", pageSize).build(), "next");
        }
        if(page > 1) {
            rb = rb.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page - 1).queryParam("pagesize", pageSize).build(), "prev");
        }

        return rb.entity(new GenericEntity<List<SeriesDTO>>(series) {}).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/lists")
    public Response createList(@PathParam("userId") @NotNull Long userId, @Valid SeriesListsNewDTO listDto) throws BadRequestException, UnauthorizedException, NotFoundException {
        Set<ConstraintViolation<SeriesListsNewDTO>> violations = validator.validate(listDto);
        if(!violations.isEmpty()) {
            throw new BadRequestException();
        }
        Optional<User> loggedInUser = userService.getLoggedUser();
        if(!loggedInUser.isPresent() || loggedInUser.get().getId() != userId){
            throw new UnauthorizedException();
        }
        long[] seriesIds = null;
        if(listDto.getSeries() != null) {
            seriesIds = new long[listDto.getSeries().size()];
            for(int i = 0; i < listDto.getSeries().size(); i++){
                seriesIds[i] = listDto.getSeries().get(i);
            }
        }
        SeriesList list = seriesService.addList(listDto.getName(),seriesIds);
        return ok(new SeriesListDTO(list, uriInfo)).build();
    }

    @DELETE
    @Path("/{userId}/lists/{listId}/series/{seriesId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeSeriesFromList(@PathParam("userId") Long userId, @PathParam("listId") Long listId, @PathParam("seriesId") Long seriesId) throws UnauthorizedException, NotFoundException {
        SeriesListDTO updatedList = new SeriesListDTO(seriesService.removeSeriesFromList(listId, seriesId), uriInfo);
        return ok(updatedList).build();
    }

    @DELETE
    @Path("/{userId}/lists/{listId}")
    public Response deleteList(@PathParam("userId")@NotNull Long userId, @PathParam("listId")@NotNull Long listId) throws NotFoundException, UnauthorizedException {
        if(userId < 0 || listId < 0){
            throw new NotFoundException();
        }
        Optional<User> loggedInUser = userService.getLoggedUser();
        if(!loggedInUser.isPresent() || loggedInUser.get().getId() != userId){
            throw new UnauthorizedException();
        }
        seriesService.removeList(listId);
        return status(Status.NO_CONTENT).build();
    }

    @POST
    @Path("/{userId}/lists/{listId}/series")
    public Response addSeriesToList(@PathParam("userId") Long userId, @PathParam("listId") Long listId, @Valid SeriesToAddDTO seriesToAddDTO) throws UnauthorizedException, NotFoundException {
        Optional<User> optUser = userService.getLoggedUser();
        if(optUser.isPresent() && optUser.get().getId() != userId) throw new UnauthorizedException();
        int result = seriesService.addSeriesToList(listId, seriesToAddDTO.getSeriesId());
        if(result == 0) {
            return Response.status(Status.NOT_MODIFIED).build();
        }
        return ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/username")
    public Response editUser(@PathParam("userId") Long userId,@Valid UsernameEditDTO usernameEditDTO) throws BadRequestException, UnauthorizedException {

        Set<ConstraintViolation<UsernameEditDTO>> violations = validator.validate(usernameEditDTO);
        if(!violations.isEmpty()) {
            throw new BadRequestException();
        }
        User loggedUser = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if (loggedUser.getId() != userId) throw new UnauthorizedException();
        userService.updateLoggedUserName(usernameEditDTO.getUserName());
        return accepted().entity(new UserDTO(userService.findById(userId).get())).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/banned")
    public Response banUser(@PathParam("userId") Long userId,@Valid BanUserDTO banUserDTO) throws BadRequestException, UnauthorizedException, NotFoundException {

        Set<ConstraintViolation<BanUserDTO>> violations = validator.validate(banUserDTO);
        if(!violations.isEmpty()) {
            throw new BadRequestException();
        }
        User loggedUser = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if (!loggedUser.getIsAdmin()) throw new UnauthorizedException();
        if (banUserDTO.getBanned()) {
            userService.banUser(userId);
        } else {
            userService.unbanUser(userId);
        }
        return accepted().entity(new UserDTO(userService.findById(userId).get())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/stats")
    public Response getUserStats(@PathParam("userId") Long userId) throws UnauthorizedException {
        Optional<User> optUser = userService.getLoggedUser();
        if(!optUser.isPresent() || optUser.get().getId() != userId) throw new UnauthorizedException();
        return ok(new GenresStatsDTO(userService.getGenresStats())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/avatar")
    public Response getUserAvatar(@PathParam("userId") Long userId) throws UnauthorizedException {
        Optional<User> user = userService.findById(userId);
        if(!user.isPresent()) {
            throw new UnauthorizedException();
        }
        return ok(new UserAvatarDTO(user.get())).build();
    }

    @PUT
    @Consumes({"image/jpeg", "image/png", "image/jpg"})
    @Path("/{userId}/avatar")
    public Response setUserAvatar(@PathParam("userId") Long userId, String base64Image) throws BadRequestException, UnauthorizedException {
        if(base64Image == null) {
            throw new BadRequestException();
        }
        if(!userService.getLoggedUser().isPresent() || userService.getLoggedUser().get().getId() != userId) {
            throw new UnauthorizedException();
        }
        userService.setUserAvatar(userId, base64Image);
        return ok().build();
    }

}
