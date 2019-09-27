package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

import java.util.Date;

public interface UserDao {

    User getUserById(long id);

    User getUserByMail(String mail);

    long createUser(String userName, String password, String mail);
}
