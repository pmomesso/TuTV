package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;

import java.util.List;
import java.util.Optional;

public interface UserService {
	User findById(long id) throws NotFoundException;

	Optional<User> findByMail(String mail);

	Either<User, Errors> createUser(String userName, String password, String mail, boolean isAdmin, String baseUrl) throws UnauthorizedException;

	Optional<User> getLoggedUser();

	List<User> getAllUsersExceptLoggedOne();

    void banUser(long userId) throws UnauthorizedException, NotFoundException;

    boolean setUserAvatar(long userId, byte[] byteArray);
	byte[] getUserAvatar(long userId);
    void unbanUser(long userId) throws UnauthorizedException, NotFoundException;

	boolean activateUser(String token);
}
