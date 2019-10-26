package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class UserDaoHibernate implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> getUserByValidationKey(String key) {
        final TypedQuery<User> query = em.createQuery("from users as u where u.confirmation_key = :key", User.class);
        query.setParameter("key", key);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> getUserByMail(String mail) {
        final TypedQuery<User> query = em.createQuery("from users as u where u.mail = :mail", User.class);
        query.setParameter("mail", mail);
        return query.getResultList().stream().findFirst();
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
        return getUserByMail(mail).isPresent();
    }

    @Override
    public boolean userNameExists(String userName) {
        TypedQuery<User> query = em.createQuery("from users as u where u.username = :username", User.class);
        query.setParameter("username", userName);
        return query.getResultList().isEmpty();
    }

    @Override
    public int updateUserName(long userId, String newUserName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkIfValidationKeyExists(String key) {
        TypedQuery<User> query = em.createQuery("from users as u where u.confirmationk_ey = :key", User.class);
        return query.setParameter("key", key).getResultList().isEmpty();
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
