package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

public interface UserService {
	User findById(long id);
	long createUser(String userName);
}
