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

@Path("users")
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
    public Response register(@Valid RegisterDTO register) {

        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(register);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }
        final URI baseUri = uriInfo.getBaseUri();
        Either<User, Collection<Errors>> u;
        try{
            u = userService.createUser(register.getUsername(),register.getPassword(),register.getMail(),false,baseUri.toString());
        }catch(UnauthorizedException e){
            return status(Status.UNAUTHORIZED).build();
        }
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
    public Response getRecentlyWatched(@PathParam("userId") Long userId) {
        List series;
        try {
            series = seriesService.getRecentlyWatchedList(userId,5).stream()
                    .map(e -> new SeriesDTO(e,userService.getLoggedUser(), uriInfo)).collect(Collectors.toList());
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        } catch (BadRequestException e) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        return ok(new GenericEntity<List<SeriesDTO>>(series) {}).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/following")
    public Response getFollowedSeries(@PathParam("userId") Long userId) {
        List seriesList;
        try {
            seriesList = seriesService.getAddedSeries(userId).stream().
                    map(series -> new SeriesDTO(series, userService.getLoggedUser(), uriInfo)).collect(Collectors.toList());
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return ok(new GenericEntity<List<SeriesDTO>>(seriesList) {}).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{userId}/following")
    public Response followSeries(@PathParam("userId") Long userId, @Valid FollowSeriesDTO followSeriesDTO) {
        Set<ConstraintViolation<FollowSeriesDTO>> violations = validator.validate(followSeriesDTO);
        if(!violations.isEmpty()) return Response.status(Status.BAD_REQUEST).build();
        try {
            Series series = seriesService.followSeries(followSeriesDTO.getSeriesId());
            return ok(new FollowersDTO(series.getFollowers(), true)).build();
        } catch (NotFoundException e ) {
            return Response.status(Status.NOT_FOUND).build();
        } catch (UnauthorizedException e) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{userId}/following/{seriesId}")
    public Response unfollowSeries(@PathParam("userId") Long userId, @PathParam("seriesId") Long seriesId) {
        //Set<ConstraintViolation<FollowSeriesDTO>> violations = validator.validate(followSeriesDTO);
        //if(!violations.isEmpty()) return Response.status(Status.BAD_REQUEST).build();
        try {
            Series series = seriesService.unfollowSeries(seriesId);
            return ok(new FollowersDTO(series.getFollowers(), false)).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        } catch (UnauthorizedException e) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    public Response getUserById(@PathParam("userId") Long userId) {
        Optional<User> optUser = userService.findById(userId);
        if(!optUser.isPresent()) {
            return status(Status.NOT_FOUND).build();
        }
        UserDTO userDTO = new UserDTO(optUser.get());
        return ok(userDTO).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/notifications")
    public Response getUserNotifications(@PathParam("userId") Long userId) {
        Optional<User> loggedInUser = userService.getLoggedUser();
        if(!loggedInUser.isPresent() || loggedInUser.get().getId() != userId) {
            return status(Status.UNAUTHORIZED).build();
        }
        List<NotificationDTO> notifications = loggedInUser.get().getNotifications().stream()
                .map(NotificationDTO::new).collect(Collectors.toList());
        return ok(new GenericEntity<List<NotificationDTO>>(notifications) {}).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/notifications/{notificationId}/viewed")
    public Response editUserNotifications(@PathParam("userId") Long userId,@PathParam("notificationId") Long notificationId, @Valid NotificationStateDTO notificationState)  {
        Set<ConstraintViolation<NotificationStateDTO>> constraintViolationSet = validator.validate(notificationState);
        if(!constraintViolationSet.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }
        if(!userService.getLoggedUser().isPresent() || userService.getLoggedUser().get().getId() != userId) {
            return status(Status.UNAUTHORIZED).build();
        }
        try {
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
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{userId}/notifications/{notificationId}")
    public Response deleteNotification(@PathParam("userId") Long userId,@PathParam("notificationId") Long notificationId)  {
        if(!userService.getLoggedUser().isPresent() || userService.getLoggedUser().get().getId() != userId) {
            return status(Status.UNAUTHORIZED).build();
        }
        try {
            userService.deleteNotification(userId,notificationId);
            return status(Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getUsersList(@QueryParam("page") Integer page, @QueryParam("pagesize") Integer pageSize) {
        UsersList usersList;
        Integer auxPage = page == null ? 1 : page;
        Integer auxPageSize = pageSize == null ? 8 : pageSize;
        try {
            usersList = userService.getAllUsersExceptLoggedOne(auxPage, auxPageSize);
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        }
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
    public Response getUserWatchList(@PathParam("userId") Long userId, @QueryParam("page") Integer page, @QueryParam("pagesize") Integer pageSize) {
        Optional<User> optUser = userService.getLoggedUser();
        if(optUser.isPresent() && optUser.get().getId() != userId) return Response.status(Status.UNAUTHORIZED).build();
        Integer auxPage = page;
        if(page == null) {
            auxPage = 1;
        }
        Integer auxPageSize = pageSize;
        if(pageSize == null) {
            auxPageSize = 20;
        }
        List<ToBeSeenEpisodeDTO> episodes = Collections.EMPTY_LIST;
        ResponseBuilder rb = ok();
        try {
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
        } catch (UnauthorizedException e) {
            return Response.status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return rb.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/upcoming")
    public Response getUserUpcomingList(@PathParam("userId") Long userId) {
        Optional<User> optUser = userService.getLoggedUser();
        if(optUser.isPresent() && optUser.get().getId() != userId) return Response.status(Status.UNAUTHORIZED).build();
        List<ToBeSeenEpisodeDTO> episodes = Collections.EMPTY_LIST;
        try {
            episodes = seriesService.getUpcomingEpisodes().stream()
                    .map(e -> new ToBeSeenEpisodeDTO(new SeriesDTO(e.getSeason().getSeries(), optUser, uriInfo),
                            new EpisodeDTO(e, optUser))).collect(Collectors.toList());
        } catch (UnauthorizedException e) {
            return Response.status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return ok(new GenericEntity<List<ToBeSeenEpisodeDTO>>(episodes) {}).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/lists")
    public Response getUserFavourites(@PathParam("userId") Long userId) {
        Optional<User> optUser = userService.getLoggedUser();
        if(!optUser.isPresent() || optUser.get().getId() != userId) {
            return status(Status.UNAUTHORIZED).build();
        }
        User user = optUser.get();
        List<SeriesListDTO> seriesLists = user.getLists().stream()
                .map(list -> new SeriesListDTO(list, uriInfo)).collect(Collectors.toList());
        return ok(new GenericEntity<List<SeriesListDTO>>(seriesLists) {}).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/lists")
    public Response createList(@PathParam("userId") @NotNull Long userId, @Valid SeriesListsNewDTO listDto){
        Set<ConstraintViolation<SeriesListsNewDTO>> violations = validator.validate(listDto);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }
        Optional<User> loggedInUser = userService.getLoggedUser();
        if(!loggedInUser.isPresent() || loggedInUser.get().getId() != userId){
            return status(Status.UNAUTHORIZED).build();
        }
        Optional<SeriesList> list;
        long[] seriesIds = null;
        if(listDto.getSeries() != null) {
            seriesIds = new long[listDto.getSeries().size()];
            for(int i = 0; i < listDto.getSeries().size(); i++){
                seriesIds[i] = listDto.getSeries().get(i);
            }
        }
        try {
            list = seriesService.addList(listDto.getName(),seriesIds);
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        }
        if(!list.isPresent()){
            return status(Status.NOT_FOUND).build();
        } else {
            return ok(new SeriesListDTO(list.get(), uriInfo)).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/lists/{listId}")
    public Response modifyList(@PathParam("userId") @NotNull Long userId, @PathParam("listId")@NotNull Long listId,
                               @Valid @NotNull SeriesListStateDTO listDto){

        Set<ConstraintViolation<SeriesListStateDTO>> violations = validator.validate(listDto);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }

        Optional<User> loggedInUser = userService.getLoggedUser();
        if(!loggedInUser.isPresent() || loggedInUser.get().getId() != userId){
            return Response.status(Status.UNAUTHORIZED).build();
        }

        long[] seriesIds = null;
        if(listDto.getSeries() != null){
            seriesIds = new long[listDto.getSeries().size()];
            for(int i = 0; i < listDto.getSeries().size(); i++){
                seriesIds[i] = listDto.getSeries().get(i);
            }
        }

        Optional<SeriesList> list;
        try {
            list = seriesService.modifyList(listId, listDto.getName(), seriesIds);
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        }
        if(!list.isPresent()){
            return status(Status.NOT_FOUND).build();
        } else {
            return ok(new SeriesListDTO(list.get(), uriInfo)).build();
        }
    }

    @DELETE
    @Path("/{userId}/lists/{listId}")
    public Response deleteList(@PathParam("userId")@NotNull Long userId, @PathParam("listId")@NotNull Long listId){
        if(userId < 0 || listId < 0){
            return status(Status.BAD_REQUEST).build();
        }
        Optional<User> loggedInUser = userService.getLoggedUser();
        if(!loggedInUser.isPresent() || loggedInUser.get().getId() != userId){
            return Response.status(Status.UNAUTHORIZED).build();
        }
        try {
            seriesService.removeList(listId);
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
        return status(Status.NO_CONTENT).build();
    }

    @POST
    @Path("/{userId}/lists/{listId}/series")
    public Response addSeriesToList(@PathParam("userId") Long userId, @PathParam("listId") Long listId, @Valid SeriesToAddDTO seriesToAddDTO) {
        Optional<User> optUser = userService.getLoggedUser();
        if(optUser.isPresent() && optUser.get().getId() != userId) return Response.status(Status.UNAUTHORIZED).build();
        int result;
        try {
            result = seriesService.addSeriesToList(listId, seriesToAddDTO.getSeriesId());
        } catch (UnauthorizedException e) {
            return Response.status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
        if(result == 0) {
            return Response.status(Status.NOT_MODIFIED).build();
        }
        return ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/username")
    public Response editUser(@PathParam("userId") Long userId,@Valid UsernameEditDTO usernameEditDTO) {

        Set<ConstraintViolation<UsernameEditDTO>> violations = validator.validate(usernameEditDTO);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }

        try {
            User loggedUser = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
            if (loggedUser.getId() != userId) return status(Status.UNAUTHORIZED).build();
            userService.updateLoggedUserName(usernameEditDTO.getUserName());
            return accepted().entity(new UserDTO(userService.findById(userId).get())).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/banned")
    public Response banUser(@PathParam("userId") Long userId,@Valid BanUserDTO banUserDTO) {

        Set<ConstraintViolation<BanUserDTO>> violations = validator.validate(banUserDTO);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }
        try {
            User loggedUser = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
            if (!loggedUser.getIsAdmin()) return status(Status.UNAUTHORIZED).build();
            if (banUserDTO.getBanned()) {
                userService.banUser(userId);
            } else {
                userService.unbanUser(userId);
            }
            return accepted().entity(new UserDTO(userService.findById(userId).get())).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/stats")
    public Response getUserStats(@PathParam("userId") Long userId) {
        try {
            Optional<User> optUser = userService.getLoggedUser();
            if(!optUser.isPresent() || optUser.get().getId() != userId) return status(Status.UNAUTHORIZED).build();
            return ok(new GenresStatsDTO(userService.getGenresStats())).build();
        } catch (UnauthorizedException e) {
            return status(Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/avatar")
    public Response getUserAvatar(@PathParam("userId") Long userId) {
        Optional<User> user = userService.findById(userId);
        if(!user.isPresent()) {
            return status(Status.NOT_FOUND).build();
        }
        if(user.get().getUserAvatar() != null) {
            return ok(new UserAvatarDTO(user.get())).build();
        } else {
            return status(Status.NO_CONTENT).build();
        }
    }

    @PUT
    @Consumes({"image/jpeg", "image/png", "image/jpg"})
    @Path("/{userId}/avatar")
    public Response setUserAvatar(@PathParam("userId") Long userId, String base64Image) {
        if(base64Image == null) {
            return status(Status.BAD_REQUEST).build();
        }
        if(!userService.getLoggedUser().isPresent() || userService.getLoggedUser().get().getId() != userId) {
            return status(Status.UNAUTHORIZED).build();
        }
        try {
            userService.setUserAvatar(userId, base64Image);
            return ok().build();
        } catch (BadRequestException e) {
            return status(Status.BAD_REQUEST).build();
        }
    }

}
