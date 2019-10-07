package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.MailService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MAIL = "mail";
    private static final String CONFIRMATION_KEY = "confirmation_key";
    private static final boolean IS_ADMIN = true;
    private static final long USER_ID = 1;
    @Mock
    private UserDao mockDao;
    @Mock
    private MailService mailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    Authentication authentication;
    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl(mockDao,mailService,passwordEncoder);

    private void assertUser(User u){
        Assert.assertEquals(USER_ID,u.getId());
        Assert.assertEquals(USERNAME,u.getUserName());
        Assert.assertEquals(PASSWORD,u.getPassword());
        Assert.assertEquals(MAIL,u.getMailAddress());
        Assert.assertEquals(CONFIRMATION_KEY,u.getConfirmationKey());
        Assert.assertEquals(IS_ADMIN,u.getIsAdmin());
    }
 //   @Test
 //   public void createUserTest(){
        //Setup
 //       final User retUser = new User(USERNAME,PASSWORD,MAIL,IS_ADMIN);
 //       retUser.setId(USER_ID);
 //       retUser.setConfirmationKey(CONFIRMATION_KEY);
 //       Mockito.when(mockDao.createUser(Mockito.eq(USERNAME), Mockito.eq(PASSWORD), Mockito.eq(MAIL), Mockito.eq(IS_ADMIN)))
   //             .thenReturn(Optional.of(retUser));
//        Mockito.when(passwordEncoder.encode(Mockito.eq(PASSWORD))).thenReturn(PASSWORD);
        //Ejercitar
        //User u  = userService.createUser(USERNAME, PASSWORD, MAIL,IS_ADMIN);
        //Asserts
        //assertUser(u); //TODO ahora el createUser recibe la baseUrl...
//    }
    @Test
    public void findByIdTest(){
        //Setup
        User mockUser = new User(USERNAME,PASSWORD,MAIL,IS_ADMIN);
        mockUser.setId(USER_ID);
        mockUser.setConfirmationKey(CONFIRMATION_KEY);
        Mockito.when(mockDao.getUserById(USER_ID))
                .thenReturn(Optional.of(mockUser));
        //Ejercitar
        User user = null;
        try {
            user = userService.findById(USER_ID);
        } catch (Exception e) {
            Assert.fail();
        }
        //Asserts
        assertUser(user);
    }
    @Test
    public void findByMailTest(){
        //Setup
        User mockUser = new User(USERNAME,PASSWORD,MAIL,IS_ADMIN);
        mockUser.setId(USER_ID);
        mockUser.setConfirmationKey(CONFIRMATION_KEY);
        Mockito.when(mockDao.getUserByMail(Mockito.eq(MAIL)))
                .thenReturn(Optional.of(mockUser));
        //Ejercitar
        Optional<User> user = userService.findByMail(MAIL);
        //Asserts
        Assert.assertTrue(user.isPresent());
        assertUser(user.get());
    }
    @Test
    public void getLoggedUserTest(){
        //Setup
        User mockUser = new User(USERNAME,PASSWORD,MAIL,IS_ADMIN);
        mockUser.setId(USER_ID);
        mockUser.setConfirmationKey(CONFIRMATION_KEY);
        Mockito.when(mockDao.getUserByMail(Mockito.eq(MAIL))).thenReturn(Optional.of(mockUser));
        Mockito.when(authentication.getName()).thenReturn(MAIL);
        userService.setAuthentication(authentication);
        //Ejercitar
        Optional<User> u = userService.getLoggedUser();
        //Asserts
        Assert.assertTrue(u.isPresent());
        assertUser(u.get());
    }
    @Test
    public void banUserTest(){
        //Setup
        User mockUser = new User(USERNAME,PASSWORD,MAIL,true);
        mockUser.setId(USER_ID);
        Mockito.when(mockDao.getUserByMail(Mockito.eq(MAIL))).thenReturn(Optional.of(mockUser));
        Mockito.when(mockDao.banUser(Mockito.eq(USER_ID))).thenAnswer(invocation -> {
            mockUser.setIsBanned(true);
            return 1;
        });
        Mockito.when(authentication.getName()).thenReturn(MAIL);
        userService.setAuthentication(authentication);
        //Ejercitar
        try {
            userService.banUser(USER_ID);
        } catch (Exception e) {
            Assert.fail();
        }
        //Asserts
        Assert.assertTrue(mockUser.getIsBanned());
    }
    @Test
    public void unbanUserTest(){
        //Setup
        User mockUser = new User(USERNAME,PASSWORD,MAIL,true);
        mockUser.setId(USER_ID);
        mockUser.setIsBanned(true);
        Mockito.when(mockDao.getUserByMail(Mockito.eq(MAIL))).thenReturn(Optional.of(mockUser));
        Mockito.when(mockDao.unbanUser(Mockito.eq(USER_ID))).thenAnswer(invocation -> {
            mockUser.setIsBanned(false);
            return 1;
        });
        Mockito.when(authentication.getName()).thenReturn(MAIL);
        userService.setAuthentication(authentication);
        //Ejercitar
        try {
            userService.unbanUser(USER_ID);
        } catch (Exception e) {
            Assert.fail();
        }
        //Asserts
        Assert.assertFalse(mockUser.getIsBanned());
    }
}
