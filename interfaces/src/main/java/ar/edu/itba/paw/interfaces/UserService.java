package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.UsersList;
import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface UserService {
	Optional<User> findById(long id);

	Optional<User> findByMail(String mail);

	Either<User, Collection<Errors>> createUser(String userName, String password, String mail, boolean isAdmin, String baseUrl) throws UnauthorizedException;
	boolean activateUser(String token);

	Optional<User> getLoggedUser();

	UsersList getAllUsersExceptLoggedOne(int page) throws UnauthorizedException ;

	Map<Genre, Long> getGenresStats() throws UnauthorizedException;

    void banUser(long userId) throws UnauthorizedException, NotFoundException;
	void unbanUser(long userId) throws UnauthorizedException, NotFoundException;

    boolean updateLoggedUserName(String newUsername) throws NotFoundException;
	void setUserAvatar(long userId, MultipartFile avatar) throws BadRequestException;
	Optional<byte[]> getUserAvatar(long userId);
}
