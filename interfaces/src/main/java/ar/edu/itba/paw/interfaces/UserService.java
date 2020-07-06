package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.UsersList;
import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface UserService {
	Optional<User> findById(long id);

	Optional<User> findByMail(String mail);

	Either<User, Collection<Errors>> createUser(String userName, String password, String mail, boolean isAdmin, String baseUrl) throws UnauthorizedException;
	Optional<User> activateUser(String token);

	Optional<User> getLoggedUser();

	UsersList getAllUsersExceptLoggedOne(int page, int pageSize) throws UnauthorizedException ;

	Map<Genre, Long> getGenresStats() throws UnauthorizedException;

    void banUser(long userId) throws UnauthorizedException, NotFoundException;
	void unbanUser(long userId) throws UnauthorizedException, NotFoundException;

    boolean updateLoggedUserName(String newUsername) throws UnauthorizedException;
	void setUserAvatar(long userId, String base64Image) throws BadRequestException;
	Optional<byte[]> getUserAvatar(long userId);

    void setNotificationViewed(long notificationId) throws NotFoundException, UnauthorizedException;
}
