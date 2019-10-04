package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

import java.util.Date;

public interface UserDao {

    User getUserById(long id);

    User getUserByMail(String mail);

    User createUser(String userName, String password, String mail,boolean isAdmin);

    boolean checkIfValidationKeyExists(String key);

    void setValidationKey(long userId, String key);

    void banUser(long userId);

    void unbanUser(long userId);
}
