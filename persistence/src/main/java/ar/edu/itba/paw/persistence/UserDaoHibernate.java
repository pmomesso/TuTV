package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoHibernate implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> getUserByValidationKey(String key) {
        final TypedQuery<User> query = em.createQuery("from User as u where u.confirmationKey = :key", User.class);
        query.setParameter("key", key);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> getUserByMail(String mail) {
        final TypedQuery<User> query = em.createQuery("from User as u where u.mailAddress = :mail", User.class);
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
        em.flush();
        return Optional.of(user);
    }

    @Override
    public boolean mailIsTaken(String mail) {
        return getUserByMail(mail).isPresent();
    }

    @Override
    public boolean userNameExists(String userName) {
        TypedQuery<User> query = em.createQuery("from User as u where u.userName = :username", User.class);
        query.setParameter("username", userName);
        return !query.getResultList().isEmpty();
    }

    @Override
    public int updateUserName(long userId, String newUserName) {
        Optional<User> user = getUserById(userId);
        if(!user.isPresent()) return -1;
        user.get().setUserName(newUserName);
        em.flush();
        return 1;
    }

    @Override
    public boolean checkIfValidationKeyExists(String key) {
        TypedQuery<User> query = em.createQuery("from User as u where u.confirmationKey = :key", User.class);
        return query.setParameter("key", key).getResultList().isEmpty();
    }

    @Override
    public void setValidationKey(long userId, String key) {
        Optional<User> user = getUserById(userId);
        user.ifPresent(u -> {
            u.setConfirmationKey(key);
            em.flush();
        });
    }

    @Override
    public int banUser(long userId) {
        Optional<User> user = getUserById(userId);
        if(!user.isPresent()) return -1;
        user.get().setIsBanned(true);
        em.flush();
        return 1;
    }

    @Override
    public Optional<byte[]> getUserAvatar(long userId) {
        TypedQuery<User> query = em.createQuery("from User as u where u.id = :userid", User.class);
        query.setParameter("userid", userId);
        Optional<User> user = query.getResultList().stream().findFirst();
        Optional<byte[]> ret = user.isPresent() ? Optional.ofNullable(user.get().getUserAvatar()) : Optional.empty();
        return ret;
    }

    @Override
    public void setUserAvatar(long userId, byte[] byteArray) {
        //Todo: ask if should change User Class
        TypedQuery<User> query = em.createQuery("from User as u where u.userId = :id", User.class);
        query.setParameter("id", userId);
        query.getResultList().stream().findFirst().ifPresent(user -> {
            user.setUserAvatar(byteArray);
            em.flush();
        });
    }

    @Override
    public int unbanUser(long userId) {
        Optional<User> user = getUserById(userId);
        if(!user.isPresent()) return -1;
        user.get().setIsBanned(false);
        em.flush();
        return 1;
    }

    @Override
    public boolean userExists(long userId) {
        return getUserById(userId).isPresent();
    }

    @Override
    public List<User> getAllUsers() {
        //Todo: ask if correct
        TypedQuery<User> query = em.createQuery("from User where true", User.class);
        return query.getResultList();
    }

}
