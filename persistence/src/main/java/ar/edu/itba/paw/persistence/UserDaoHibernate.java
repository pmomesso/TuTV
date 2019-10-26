package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class UserDaoHibernate implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> getUserById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> getUserByValidationKey(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> getUserByMail(String mail) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> createUser(String userName, String password, String mail, boolean isAdmin) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setMailAddress(mail);
        user.setIsAdmin(isAdmin);
        em.persist(user);
        return Optional.of(user);
    }

    @Override
    public boolean mailIsTaken(String mail) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean userNameExists(String userName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int updateUserName(long userId, String newUserName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkIfValidationKeyExists(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValidationKey(long userId, String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int banUser(long userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<byte[]> getUserAvatar(long userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUserAvatar(long userId, byte[] byteArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int unbanUser(long userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean userExists(long userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> getAllUsers() {
        throw new UnsupportedOperationException();
    }

}
