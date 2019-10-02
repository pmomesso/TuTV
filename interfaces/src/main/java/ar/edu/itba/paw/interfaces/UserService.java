package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

public interface UserService {
	User findById(long id);

	User findByMail(String mail);

	User createUser(String userName, String password, String mail,boolean isAdmin);

	User getLoggedUser();
}
