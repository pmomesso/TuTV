package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {
	Optional<User> findById(long id);

	Optional<User> findByMail(String mail);

	Either<User, Collection<Errors>> createUser(String userName, String password, String mail, boolean isAdmin, String baseUrl) throws UnauthorizedException;
	boolean activateUser(String token);

	Optional<User> getLoggedUser();

	List<User> getAllUsersExceptLoggedOne();

    void banUser(long userId) throws UnauthorizedException, NotFoundException;
	void unbanUser(long userId) throws UnauthorizedException, NotFoundException;

    boolean updateLoggedUserName(String newUsername) throws NotFoundException;
	void setUserAvatar(long userId, byte[] byteArray);
	Optional<byte[]> getUserAvatar(long userId);
}
