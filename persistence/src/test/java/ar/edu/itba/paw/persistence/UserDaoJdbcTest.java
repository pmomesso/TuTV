package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import org.junit.After;
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
public class UserDaoJdbcTest {

    private static final long USER_ID = 1;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MAIL = "mail";
    private static final String CONFIRMATION_KEY = "confirmation_key";
    private static final boolean IS_ADMIN = true;
    @Autowired
    private DataSource ds;
    @Autowired
    private UserDaoJdbc userDao;
    private JdbcTemplate jdbcTemplate;

    private void assertUser(User user){
        Assert.assertNotNull(user);
        Assert.assertEquals(USERNAME,user.getUserName());
        Assert.assertEquals(PASSWORD,user.getPassword());
        Assert.assertEquals(MAIL,user.getMailAddress());
        Assert.assertEquals(USER_ID,user.getId());
        Assert.assertEquals(IS_ADMIN,user.getIsAdmin());
        Assert.assertEquals(CONFIRMATION_KEY,user.getConfirmationKey());
    }
    private void insertUser(){
        jdbcTemplate.execute(String.format("INSERT INTO users(id, username, password, mail,confirmation_key,isAdmin) VALUES( %d,'%s','%s','%s','%s',%b)",
                USER_ID, USERNAME, PASSWORD, MAIL,CONFIRMATION_KEY,IS_ADMIN));
    }
    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
    }
    @After
    public void clearDatabase() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }
    @Test
    public void createUserTest(){
        //Ejercitar
        final User u = userDao.createUser(USERNAME, PASSWORD, MAIL,IS_ADMIN);
        //Asserts
        Assert.assertTrue(u.getId() >= 0);
        Assert.assertEquals(USERNAME,u.getUserName());
        Assert.assertEquals(PASSWORD,u.getPassword());
        Assert.assertEquals(MAIL,u.getMailAddress());
        Assert.assertEquals(IS_ADMIN,u.getIsAdmin());
    }
    @Test
    public void getUserByIdTest(){
        //Setup
        insertUser();
        //Ejercitar
        final User user = userDao.getUserById(USER_ID);
        //Asserts
        assertUser(user);
    }
    @Test
    public void getUserByEmailTest(){
        //Setup
        insertUser();
        //Ejercitar
        final User user = userDao.getUserByMail(MAIL);
        //Asserts
        assertUser(user);
    }
    @Test
    public void checkIfValidationKeyExistsTest(){
        //Setup
        insertUser();
        //Ejercitar
        final boolean exists = userDao.checkIfValidationKeyExists(CONFIRMATION_KEY);
        //Asserts
        Assert.assertTrue(exists);
    }
    @Test
    public void setValidationKeyTest(){
        //Setup
        insertUser();
        final String newConfirmationKey = CONFIRMATION_KEY + "_new";
        //Ejercitar
        userDao.setValidationKey(USER_ID,newConfirmationKey);
        //Asserts
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users",
                String.format("id=%d AND confirmation_key='%s'",USER_ID,newConfirmationKey)));
    }
    @Test
    public void banUserTest(){
        //Setup
        insertUser();
        jdbcTemplate.update("UPDATE users SET isBanned = false WHERE id = ?",USER_ID);
        //Ejercitar
        userDao.banUser(USER_ID);
        //Asserts
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users",
                String.format("id=%d AND isBanned=true",USER_ID)));
    }
    @Test
    public void unbanUserTest(){
        //Setup
        insertUser();
        jdbcTemplate.update("UPDATE users SET isBanned = true WHERE id = ?",USER_ID);
        //Ejercitar
        userDao.unbanUser(USER_ID);
        //Asserts
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users",
                String.format("id=%d AND isBanned=false",USER_ID)));
    }
    @Test
    public void getUserByWrongIdTest(){
        //Setup
        insertUser();
        //Ejercitar
        final User user = userDao.getUserById(USER_ID + 1);
        //Asserts
        Assert.assertNull(user);
    }
    @Test
    public void getUserByWrongEmailTest(){
        //Setup
        insertUser();
        //Ejercitar
        final User user = userDao.getUserByMail(MAIL + "wrong");
        //Asserts
        Assert.assertNull(user);
    }
    @Test
    public void checkIfWrongValidationKeyExistsTest(){
        //Setup
        insertUser();
        //Ejercitar
        final boolean exists = userDao.checkIfValidationKeyExists(CONFIRMATION_KEY + "wrong");
        //Asserts
        Assert.assertFalse(exists);
    }
    @Test
    public void banUserByWrongIdTest(){
        //Setup
        insertUser();
        jdbcTemplate.update("UPDATE users SET isBanned = false WHERE id = ?",USER_ID);
        //Ejercitar
        userDao.banUser(USER_ID + 1);
        //Asserts
        Assert.assertEquals(0,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users",
                String.format("id=%d AND isBanned=true",USER_ID)));
    }
    @Test
    public void unbanUserByWrongIdTest(){
        //Setup
        insertUser();
        jdbcTemplate.update("UPDATE users SET isBanned = true WHERE id = ?",USER_ID);
        //Ejercitar
        userDao.unbanUser(USER_ID + 1);
        //Asserts
        Assert.assertEquals(0,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users",
                String.format("id=%d AND isBanned=false",USER_ID)));
    }
}
