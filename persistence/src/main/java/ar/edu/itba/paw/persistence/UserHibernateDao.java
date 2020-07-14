package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class UserHibernateDao implements UserDao {

    private static Integer OFFSET = 9;

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
        return !query.setParameter("key", key).getResultList().isEmpty();
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
        TypedQuery<User> query = em.createQuery("from User as u where u.id = :id", User.class);
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
    public UsersList getAllUsers(int page, int pageSize, long userId) {
        if(page <=0 ){
            page = 1;
        }
        if(pageSize <= 0) {
            pageSize = OFFSET;
        }
        UsersList usersList = new UsersList();

        List<User> list = em.createNativeQuery("SELECT * " +
                "FROM users WHERE users.id != ? " +
                "LIMIT ? OFFSET ?", User.class)
                .setParameter(1, userId)
                .setParameter(2, pageSize)
                .setParameter(3, (page-1)*pageSize)
                .getResultList();

        TypedQuery<Long> countQuery = em.createQuery("select count(*) from User", Long.class);

        usersList.setTotal(countQuery.getSingleResult() - 1);
        usersList.setFrom((page - 1) * pageSize + 1);
        if (usersList.getTotal() < (page - 1) * pageSize + 1 + pageSize) {
            usersList.setTo((usersList.getTotal()) % pageSize + (page - 1) * pageSize);
        } else {
            usersList.setTo(new Long((page - 1) * pageSize +  pageSize));
        }
        usersList.setArePrevious(((page - 1) * pageSize) > (pageSize - 1));
        int added = ((usersList.getTotal() % pageSize) == 0) ? 0 : 1;
        usersList.setAreNext(page < (usersList.getTotal()/pageSize + added));
        usersList.setUsersList(list);

        return usersList;
    }

    private List<Genre> getUserGenres(long userId) {
        TypedQuery<Genre> query = em.createQuery("select genre from User as u inner join u.follows as series inner join series.genres as genre where u.id = :id", Genre.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }

    private Long getCountSeriesByGenre(long genreId, long userId) {
        TypedQuery<Long> countQuery = em.createQuery("select count(*) from User as u inner join u.follows as series inner join series.genres as genre " +
                "WHERE genre.id = :id AND u.id = :userid", Long.class);
        countQuery.setParameter("id", genreId);
        countQuery.setParameter("userid", userId);
        return countQuery.getSingleResult();
    }

    @Override
    public Map<Genre, Long> getGenreStats(long userId) {
        Map<Genre, Long> genreStats = new HashMap<>();

        for (Genre genre : getUserGenres(userId)) {
            genreStats.put(genre, getCountSeriesByGenre(genre.getId(), userId));
        }

        return genreStats;
    }

    @Override
    public boolean setNotificationViewed(long notificationId,boolean viewed) {
        Optional<Notification> n = Optional.ofNullable(em.find(Notification.class, notificationId));
        n.ifPresent(notification -> notification.setViewed(viewed));
        return n.isPresent();
    }

    @Override
    public boolean deleteNotification(long userId, long notificationId){
        int isSuccessful = em.createQuery("delete from Notification n where n.id = :notificationId and n.user.id = :userId")
                .setParameter("notificationId", notificationId)
                .setParameter("userId", userId)
                .executeUpdate();
        return isSuccessful > 0;
    }

}
