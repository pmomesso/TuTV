package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")

public class UserDaoImplTest {

    private static final long USER_ID = 1;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MAIL = "mail";
    @Autowired
    private DataSource ds;
    @Autowired
    private UserDaoJdbc userDao;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }
    @Test
    public void testCreate(){
        //Ejercitar
        final long id = userDao.createUser(USERNAME, PASSWORD, MAIL);
        //Asserts
        Assert.assertTrue(id >= 0);
    }
    @Test
    public void testGetUser(){
        //Setup
        jdbcTemplate.execute(String.format("INSERT INTO users VALUES( %d,'%s','%s', '%s')", USER_ID, USERNAME, PASSWORD, MAIL));

        //Ejercitar
        final User user = userDao.getUser(USER_ID);
        //Asserts
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getUserName(),USERNAME);
        Assert.assertEquals(user.getPassword(), PASSWORD);
        Assert.assertEquals(user.getMailAddress(), MAIL);
        Assert.assertEquals(user.getId(), USER_ID);
    }
}
