package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

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
    private static final String AVATAR = "X'FFD91A5817BBF84A'";
    @Autowired
    private DataSource ds;
    @Autowired
    private UserDaoJdbc userDao;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

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
        Map<String,Object> args = new HashMap<>();
        args.put("id",USER_ID);
        args.put("username", USERNAME);
        args.put("password", PASSWORD);
        args.put("mail", MAIL);
        args.put("isAdmin",IS_ADMIN);
        args.put("confirmation_key",CONFIRMATION_KEY);
        //TODO
        //args.put("avatar",AVATAR);
        jdbcInsert.execute(args);
    }
    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users");
    }
    @After
    public void clearDatabase() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }
    @Test
    public void createUserTest(){
        //Ejercitar
        final Optional<User> u = userDao.createUser(USERNAME, PASSWORD, MAIL,IS_ADMIN);
        //Asserts
        Assert.assertTrue(u.isPresent());
        Assert.assertTrue(u.get().getId() >= 0);
        Assert.assertEquals(USERNAME,u.get().getUserName());
        Assert.assertEquals(PASSWORD,u.get().getPassword());
        Assert.assertEquals(MAIL,u.get().getMailAddress());
        Assert.assertEquals(IS_ADMIN,u.get().getIsAdmin());
    }
    @Test
    public void getUserByIdTest(){
        //Setup
        insertUser();
        //Ejercitar
        final Optional<User> user = userDao.getUserById(USER_ID);
        //Asserts
        Assert.assertTrue(user.isPresent());
        assertUser(user.get());
    }
    @Test
    public void getUserByValidationKeyTest(){
        //Setup
        insertUser();
        //Ejercitar
        final Optional<User> user = userDao.getUserByValidationKey(CONFIRMATION_KEY);
        //Asserts
        Assert.assertTrue(user.isPresent());
        assertUser(user.get());

    }
    @Test
    public void getUserByEmailTest(){
        //Setup
        insertUser();
        //Ejercitar
        final Optional<User> user = userDao.getUserByMail(MAIL);
        //Asserts
        Assert.assertTrue(user.isPresent());
        assertUser(user.get());
    }
    @Test
    public void mailIsTakenTest(){
        //Setup
        insertUser();
        //Ejercitar
        boolean taken = userDao.mailIsTaken(MAIL);
        //Asserts
        Assert.assertTrue(taken);
    }
    @Test
    public void userNameExistsTest(){
        //Setup
        insertUser();
        //Ejercitar
        boolean taken = userDao.userNameExists(USERNAME);
        //Asserts
        Assert.assertTrue(taken);
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
        int result = userDao.banUser(USER_ID);
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
        int result = userDao.unbanUser(USER_ID);
        //Asserts
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users",
                String.format("id=%d AND isBanned=false",USER_ID)));
    }
    @Test
    //TODO
    public void getUserAvatarTest(){
        /*
        //Setup
        insertUser();
        //Ejercitar
        byte[] avatar = userDao.getUserAvatar(USER_ID);
        //Asserts
        Assert.assertEquals(AVATAR.getBytes(),avatar);
        */
    }
    @Test
    public void getUserByWrongIdTest(){
        //Setup
        insertUser();
        //Ejercitar
        final Optional<User> user = userDao.getUserById(USER_ID + 1);
        //Asserts
        Assert.assertFalse(user.isPresent());
        Assert.assertEquals(user, Optional.empty());
    }
    @Test
    public void getUserByWrongEmailTest(){
        //Setup
        insertUser();
        //Ejercitar
        final Optional<User> user = userDao.getUserByMail(MAIL + "wrong");
        //Asserts
        Assert.assertFalse(user.isPresent());
        Assert.assertEquals(user, Optional.empty());
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
        int result = userDao.banUser(USER_ID + 1);
        //Asserts
        Assert.assertEquals(-1,result);
        Assert.assertEquals(0,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users",
                String.format("id=%d AND isBanned=true",USER_ID)));
    }
    @Test
    public void unbanUserByWrongIdTest(){
        //Setup
        insertUser();
        jdbcTemplate.update("UPDATE users SET isBanned = true WHERE id = ?",USER_ID);
        //Ejercitar
        int result = userDao.unbanUser(USER_ID + 1);
        //Asserts
        Assert.assertEquals(-1,result);
        Assert.assertEquals(0,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users",
                String.format("id=%d AND isBanned=false",USER_ID)));
    }
}
