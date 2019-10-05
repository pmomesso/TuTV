package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

public interface UserDao {

    User getUserById(long id);

    User getUserByMail(String mail);

    User createUser(String userName, String password, String mail,boolean isAdmin);

    boolean checkIfValidationKeyExists(String key);

    void setValidationKey(long userId, String key);

    int banUser(long userId);

    byte[] getUserAvatar(long userId);
    void setUserAvatar(long userId, byte[] byteArray);
    int unbanUser(long userId);

    boolean userExists(long userId);
}
