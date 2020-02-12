package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
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

    /* Valores test serie */
    private static final int TVDB_ID = 2;
    private static final String SERIES_NAME_1 = "name";
    private static final String SERIES_NAME_2 = "series name 2";
    private static final String DESCRIPTION = "description";
    private static final double TOTAL_RATING = 3;
    private static final String STATUS = "status";
    private static final int RUNTIME = 4;
    private static final String FIRST_AIRED = "2000-01-01";
    private static final String ID_IMDB = "5";
    private static final String ADDED = "2000-01-01";
    private static final String UPDATED = "2000-01-01";
    private static final String POSTER_URL = "url/poster";
    private static final String BANNER_URL = "url/banner";
    private static final int FOLLOWERS = 6;
    /* Valores test para usuario*/
    private static final String USERNAME = "username";
    private static final String MAILADDRESS = "mailaddress";
    private static final String PASSWORD = "password";
    private static final String CONFIRMATION_KEY = "validation key";
    private static final String CONFIRMATION_KEY_2 = "validation key 2";
    private static final boolean ISADMIN = true;
    private static final boolean BANNED = false;


    /* Valores test para genre*/
    private static final String GENRE_1 = "genre 1";
    private static final String GENRE_2 = "genre 2";
    private static final String GENRE1_I18KEY = "genre1";
    private static final String GENRE2_I18KEY = "genre2";

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
        genre1.setI18Key(GENRE1_I18KEY);
        genre2 = new Genre(GENRE_2);
        genre2.setI18Key(GENRE2_I18KEY);
        em.persist(genre1);
        em.persist(genre2);
        Network network = new Network("name");
        em.persist(network);
        series1 = new Series(TVDB_ID, SERIES_NAME_1,DESCRIPTION, network,POSTER_URL,BANNER_URL,TOTAL_RATING,STATUS,RUNTIME,FOLLOWERS,ID_IMDB,FIRST_AIRED,ADDED,UPDATED);
        series2 = new Series(TVDB_ID, SERIES_NAME_2,DESCRIPTION, network,POSTER_URL,BANNER_URL,TOTAL_RATING,STATUS,RUNTIME,FOLLOWERS,ID_IMDB,FIRST_AIRED,ADDED,UPDATED);
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
        Optional<User> user = userHibernateDao.createUser(USERNAME, PASSWORD, MAILADDRESS, ISADMIN);
        assertTrue(user.isPresent());
        User u = user.get();
        assertEquals(USERNAME, u.getUserName());
        assertEquals(MAILADDRESS, u.getMailAddress());
        assertEquals(PASSWORD, u.getPassword());
        assertEquals(ISADMIN, u.getIsAdmin());
        assertEquals(BANNED, u.getIsBanned());
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
