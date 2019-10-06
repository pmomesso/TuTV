package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;

import java.util.Optional;

public interface UserService {
	User findById(long id) throws NotFoundException;

	Optional<User> findByMail(String mail) throws NotFoundException;

	User createUser(String userName, String password, String mail, boolean isAdmin, String baseUrl);

	Optional<User> getLoggedUser() throws NotFoundException;

    void banUser(long userId) throws UnauthorizedException, NotFoundException;

    boolean setUserAvatar(long userId, byte[] byteArray);
	byte[] getUserAvatar(long userId);
    void unbanUser(long userId) throws UnauthorizedException, NotFoundException;

	boolean activateUser(String token);
}
