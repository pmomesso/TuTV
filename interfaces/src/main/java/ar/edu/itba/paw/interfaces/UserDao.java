package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UsersList;

import java.util.Map;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById(long id);

    Optional<User> getUserByValidationKey(String key);

    Optional<User> getUserByMail(String mail);

    Optional<User> createUser(String userName, String password, String mail, boolean isAdmin);

    boolean mailIsTaken(String mail);

    boolean userNameExists(String userName);

    int updateUserName(long userId, String newUserName);

    boolean checkIfValidationKeyExists(String key);

    void setValidationKey(long userId, String key);

    int banUser(long userId);

    Optional<byte[]> getUserAvatar(long userId);
    void setUserAvatar(long userId, byte[] byteArray);
    int unbanUser(long userId);

    boolean userExists(long userId);

    UsersList getAllUsers(int page, int pageSize, long userId);

    Map<Genre, Long> getGenreStats(long userId);

    boolean setNotificationViewed(long notificationId, boolean viewed);

    boolean deleteNotification(long userId, long notificationId);
}
