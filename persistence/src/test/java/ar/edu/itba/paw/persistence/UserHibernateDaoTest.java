package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserHibernateDaoTest {
    /* Valores test para usuario*/
    private static final String USERNAME = "username";
    private static final String MAILADDRESS = "mailaddress";
    private static final String PASSWORD = "password";
    private static final String CONFIRMATION_KEY = "validation key";
    private static final String CONFIRMATION_KEY_2 = "validation key 2";
    private static final boolean ISADMIN = true;
    private static final boolean BANNED = false;

    /* Valores test para serie*/
    private static final String SERIES_NAME_1 = "series name 1";
    private static final String SERIES_NAME_2 = "series name 2";

    /* Valores test para genre*/
    private static final String GENRE_1 = "genre 1";
    private static final String GENRE_2 = "genre 2";

    private User user;
    private Series series1;
    private Series series2;
    private Genre genre1;
    private Genre genre2;
    private Notification notification;

    @Autowired
    private UserHibernateDao userHibernateDao;
    @PersistenceContext
    private EntityManager em;

    /* Métodos helpers para testear*/
    private void assertUser(User u) {
        assertNotNull(u);
        assertEquals(USERNAME, u.getUserName());
        assertEquals(MAILADDRESS, u.getMailAddress());
        assertEquals(PASSWORD, u.getPassword());
        assertEquals(CONFIRMATION_KEY, u.getConfirmationKey());
        assertEquals(ISADMIN, u.getIsAdmin());
        assertEquals(BANNED, u.getIsBanned());
    }

    @Before
    public void populateDatabase() {
        user = new User();
        user.setUserName(USERNAME);
        user.setPassword(PASSWORD);
        user.setMailAddress(MAILADDRESS);
        user.setConfirmationKey(CONFIRMATION_KEY);
        user.setIsAdmin(ISADMIN);
        user.setIsBanned(BANNED);
        em.persist(user);

        genre1 = new Genre(GENRE_1);
        genre2 = new Genre(GENRE_2);
        em.persist(genre1);
        em.persist(genre2);

        series1 = new Series();
        series1.setName(SERIES_NAME_1);
        series2 = new Series();
        series2.setName(SERIES_NAME_2);
        em.persist(series1);
        em.persist(series2);

        series1.addUserFollower(user);
        user.getFollows().add(series1);

        series1.getGenres().add(genre1);
        genre1.getSeries().add(series1);

        series2.getGenres().add(genre2);
        genre2.getSeries().add(series2);

        notification = new Notification();
        notification.setViewed(false);
        em.persist(notification);

        user.getNotifications().add(notification);
        notification.setUser(user);

        em.flush();
    }

    @Test
    public void createUserTest() {
        Optional<User> u = userHibernateDao.createUser(USERNAME, PASSWORD, MAILADDRESS, ISADMIN);
        assertTrue(u.isPresent());
        assertUser(u.get());
    }

    @Test
    public void getUserByIdTest() {
        Optional<User> u = userHibernateDao.getUserById(user.getId());
        assertTrue(u.isPresent());
        assertEquals(user.getId(), u.get().getId());
        assertUser(u.get());
    }

    @Test
    public void getUserByMailTest() {
        Optional<User> u = userHibernateDao.getUserByMail(MAILADDRESS);
        assertTrue(u.isPresent());
        assertEquals(user.getId(), u.get().getId());
        assertUser(u.get());
    }

    @Test
    public void getUserByValidationKeyTest() {
        Optional<User> u = userHibernateDao.getUserByValidationKey(CONFIRMATION_KEY);
        assertTrue(u.isPresent());
        assertEquals(user.getId(), u.get().getId());
        assertUser(u.get());
    }

    @Test
    public void mailIsTakenTest() {
        assertTrue(userHibernateDao.mailIsTaken(MAILADDRESS));
    }

    @Test
    public void validationKeyExistsTest() {
        assertTrue(userHibernateDao.checkIfValidationKeyExists(CONFIRMATION_KEY));
    }

    @Test
    public void userExistsTest() {
        assertTrue(userHibernateDao.userExists(user.getId()));
    }

    @Test
    public void banAndUnbanUserTest() {
        userHibernateDao.banUser(user.getId());
        assertTrue(user.getIsBanned());
        userHibernateDao.unbanUser(user.getId());
        assertFalse(user.getIsBanned());
    }

    @Test
    public void setValidationKeyTest() {
        userHibernateDao.setValidationKey(user.getId(), CONFIRMATION_KEY_2);
        assertEquals(CONFIRMATION_KEY_2, user.getConfirmationKey());
        userHibernateDao.setValidationKey(user.getId(), CONFIRMATION_KEY);
        assertEquals(CONFIRMATION_KEY, user.getConfirmationKey());
    }

    @Test
    public void getGenreStatsTest() {
        Map<Genre, Long> genreStats = userHibernateDao.getGenreStats(user.getId());

        assertTrue(genreStats.containsKey(genre1) && !genreStats.containsKey(genre2));
        assertEquals(1, (long)genreStats.get(genre1));
    }

    @Test
    public void setNotificationViewedTest() {
        assertTrue(userHibernateDao.setNotificationViewed(notification.getId()));
        notification.setViewed(false);
    }

}
