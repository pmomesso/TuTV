package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;

public interface UserService {
	User findById(long id) throws NotFoundException;

	User findByMail(String mail);

	User createUser(String userName, String password, String mail,boolean isAdmin);

	User getLoggedUser();

    void banUser(long userId) throws UnauthorizedException, NotFoundException;

    boolean setUserAvatar(long userId, byte[] byteArray);
	byte[] getUserAvatar(long userId);
    void unbanUser(long userId) throws UnauthorizedException, NotFoundException;
}
