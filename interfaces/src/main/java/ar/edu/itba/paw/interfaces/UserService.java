package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;

public interface UserService {
	User findById(long id) throws NotFoundException;

	User findByMail(String mail);

	User createUser(String userName, String password, String mail,boolean isAdmin);

	User getLoggedUser();

    void banUser(long userId) throws BadRequestException;

    void unbanUser(long userId) throws BadRequestException;
}
