package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.MailService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UsersList;
import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.ApiException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MAIL = "mail";
    private static final String CONFIRMATION_KEY = "confirmation_key";
    private static final boolean IS_ADMIN = true;
    private static final long USER_ID = 1;
    private static final byte[] AVATAR = new byte[1024];

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

    @BeforeClass
    public static void generateAvatar(){
        new Random().nextBytes(AVATAR);
    }

    private User getMockUser(){
        User mockUser = new User(USERNAME,PASSWORD,MAIL,IS_ADMIN);
        mockUser.setId(USER_ID);
        mockUser.setConfirmationKey(CONFIRMATION_KEY);
        return mockUser;
    }
    private void assertUser(User u){
        Assert.assertEquals(USER_ID,u.getId());
        Assert.assertEquals(USERNAME,u.getUserName());
        Assert.assertEquals(PASSWORD,u.getPassword());
        Assert.assertEquals(MAIL,u.getMailAddress());
        Assert.assertEquals(CONFIRMATION_KEY,u.getConfirmationKey());
        Assert.assertEquals(IS_ADMIN,u.getIsAdmin());
    }
    @Test
    public void createUserTest(){
        //Setup
        final User retUser = getMockUser();
        Mockito.when(mockDao.createUser(Mockito.eq(USERNAME), Mockito.eq(PASSWORD), Mockito.eq(MAIL), Mockito.eq(IS_ADMIN)))
                .thenReturn(Optional.of(retUser));
        Mockito.when(passwordEncoder.encode(Mockito.eq(PASSWORD))).thenReturn(PASSWORD);
        //Ejercitar
        Either<User, Collection<Errors>> either  = userService.createUser(USERNAME, PASSWORD, MAIL,IS_ADMIN,"baseurl");
        //Asserts
        Assert.assertTrue(either.isValuePresent());
        User u = either.getValue();
        Assert.assertEquals(USER_ID,u.getId());
        Assert.assertEquals(USERNAME,u.getUserName());
        Assert.assertEquals(PASSWORD,u.getPassword());
        Assert.assertEquals(MAIL,u.getMailAddress());
        Assert.assertTrue(u.getConfirmationKey().length() > 0);
        Assert.assertEquals(IS_ADMIN,u.getIsAdmin());
    }
    @Test
    public void findByIdTest(){
        //Setup
        User mockUser = getMockUser();
        Mockito.when(mockDao.getUserById(USER_ID))
                .thenReturn(Optional.of(mockUser));
        //Ejercitar
        Optional<User> user = userService.findById(USER_ID);
        //Asserts
        Assert.assertTrue(user.isPresent());
        assertUser(user.get());
    }
    @Test
    public void findByMailTest(){
        //Setup
        User mockUser = getMockUser();
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
        User mockUser = getMockUser();
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
    public void updateLoggedUserNameTest(){
        //Setup
        User mockUser = getMockUser();
        final String newUsername = USERNAME + "_new";
        Mockito.when(mockDao.getUserByMail(Mockito.eq(MAIL))).thenReturn(Optional.of(mockUser));
        Mockito.when(mockDao.updateUserName(Mockito.eq(USER_ID),Mockito.eq(newUsername))).thenAnswer(invocation -> {
           mockUser.setUserName(newUsername);
           return 1;
        });
        Mockito.when(authentication.getName()).thenReturn(MAIL);
        userService.setAuthentication(authentication);
        //Ejercitar
        boolean result = false;
        try {
            result = userService.updateLoggedUserName(newUsername);
        } catch (NotFoundException e) {
            Assert.fail();
            return;
        }
        //Asserts
        Assert.assertTrue(result);
        Assert.assertEquals(newUsername,mockUser.getUserName());
    }
    @Test
    public void loggedInGetAllUsersButLoggedOneTest(){
        //Setup
        User mockUser = getMockUser();
        Mockito.when(mockDao.getAllUsers(1, 1)).thenAnswer(invocation -> {
            User[] users = new User[1];
            users[0] = mockUser;
            return Arrays.asList(users);
        });
        Mockito.when(mockDao.getUserByMail(Mockito.eq(MAIL))).thenReturn(Optional.of(mockUser));
        Mockito.when(authentication.getName()).thenReturn(MAIL);
        userService.setAuthentication(authentication);
        //Ejercitar
        UsersList users;
        try {
            users = userService.getAllUsersExceptLoggedOne(1);
        } catch (UnauthorizedException e) {
            Assert.fail();
            return;
        }
        //Asserts
        Assert.assertEquals(0,users.getUsersList().size());
    }
    @Test
    public void loggedOutGetAllUsersButLoggedOneTest(){
        //Setup
        User mockUser = getMockUser();
        Mockito.when(mockDao.getAllUsers(1, 1)).thenAnswer(invocation -> {
            User[] users = new User[1];
            users[0] = mockUser;
            return Arrays.asList(users);
        });
        //Ejercitar
        UsersList users;
        try {
            users = userService.getAllUsersExceptLoggedOne(1);
        } catch (UnauthorizedException e) {
            Assert.fail();
            return;
        }
        //Asserts
        Assert.assertEquals(1,users.getUsersList().size());
        assertUser(users.getUsersList().get(0));
    }
    @Test
    public void activateUserTest(){
        //Setup
        User mockUser = getMockUser();
        Mockito.when(mockDao.getUserByValidationKey(Mockito.eq(CONFIRMATION_KEY))).thenReturn(Optional.of(mockUser));
        Mockito.doAnswer(invocation -> {
            mockUser.setConfirmationKey(null);
            return mockUser;
        }).when(mockDao).setValidationKey(Mockito.eq(USER_ID),Mockito.eq(null));
        //Ejercitar
        boolean activateUser = userService.activateUser(CONFIRMATION_KEY);
        //Asserts
        Assert.assertTrue(activateUser);
        Assert.assertNull(mockUser.getConfirmationKey());
    }
    @Test
    public void banUserTest(){
        //Setup
        User mockUser = getMockUser();
        mockUser.setIsBanned(false);
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
        User mockUser = getMockUser();
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
    @Test
    public void getUserAvatarTest() {
        //Setup
        Mockito.doReturn(Optional.of(AVATAR)).when(mockDao).getUserAvatar(Mockito.eq(USER_ID));
        //Ejercitar
        Optional<byte[]> avatar = userService.getUserAvatar(USER_ID);
        //Asserts
        Assert.assertTrue(avatar.isPresent());
        Assert.assertArrayEquals(AVATAR,avatar.get());
    }
    @Test
    public void findByWrongIdTest(){
        //Setup
        User mockUser = getMockUser();
        Mockito.when(mockDao.getUserById(AdditionalMatchers.not(Mockito.eq(USER_ID))))
                .thenReturn(Optional.empty());
        //Ejercitar
        Optional<User> user = userService.findById(USER_ID + 1);
        //Si no lanza excepcion, falla el test.
        Assert.assertFalse(user.isPresent());
    }
    @Test
    public void findByWrongMailTest(){
        //Setup
        User mockUser = getMockUser();
        Mockito.when(mockDao.getUserByMail(AdditionalMatchers.not(Mockito.eq(MAIL))))
                .thenReturn(Optional.empty());
        //Ejercitar
        Optional<User> user = userService.findByMail(MAIL + "other");
        //Asserts
        Assert.assertFalse(user.isPresent());
    }
    @Test
    public void getLoggedUserWhenLoggedOutTest(){
        //Setup
        User mockUser = getMockUser();
        Mockito.when(mockDao.getUserByMail(AdditionalMatchers.not(Mockito.eq(MAIL))))
                .thenReturn(Optional.empty());
        //Ejercitar
        Optional<User> u = userService.getLoggedUser();
        //Asserts
        Assert.assertFalse(u.isPresent());;
    }
    @Test
    public void updateLoggedUserNameWhenLoggedOutTest(){
        //Setup
        User mockUser = getMockUser();
        final String newUsername = USERNAME + "_new";
        Mockito.when(mockDao.getUserByMail(AdditionalMatchers.not(Mockito.eq(MAIL))))
                .thenReturn(Optional.empty());
        //Ejercitar
        boolean result = false;
        try {
            result = userService.updateLoggedUserName(newUsername);
        } catch (NotFoundException e) {
            Assert.assertFalse(result);
            return;
        }
        //Si no lanza excepcion, falla el test.
        Assert.fail();
    }
    @Test
    public void activateUserByWrongConfirmationKeyTest(){
        //Setup
        User mockUser = getMockUser();
        Mockito.when(mockDao.getUserByValidationKey(AdditionalMatchers.not(Mockito.eq(CONFIRMATION_KEY)))).thenReturn(Optional.empty());
        //Ejercitar
        boolean activateUser = userService.activateUser(CONFIRMATION_KEY + "other");
        //Asserts
        Assert.assertFalse(activateUser);
        Assert.assertNotNull(mockUser.getConfirmationKey());
    }
    @Test
    public void banUserByWrongIdTest(){
        //Setup
        User mockUser = getMockUser();
        mockUser.setIsBanned(false);
        Mockito.when(mockDao.getUserByMail(Mockito.eq(MAIL))).thenReturn(Optional.of(mockUser));
        Mockito.when(mockDao.banUser(AdditionalMatchers.not(Mockito.eq(USER_ID)))).thenReturn(-1);
        Mockito.when(authentication.getName()).thenReturn(MAIL);
        userService.setAuthentication(authentication);
        //Ejercitar
        try {
            userService.banUser(USER_ID + 1);
        } catch (NotFoundException e) {
            return;
        } catch (UnauthorizedException e) {
            Assert.fail();
        }
        //Si no lanza excepcion, falla el test.
        Assert.fail();
    }
    @Test
    public void unbanUserByWrongIdTest(){
        //Setup
        User mockUser = getMockUser();
        mockUser.setIsBanned(true);
        Mockito.when(mockDao.getUserByMail(Mockito.eq(MAIL))).thenReturn(Optional.of(mockUser));
        Mockito.when(mockDao.unbanUser(AdditionalMatchers.not(Mockito.eq(USER_ID)))).thenReturn(-1);
        Mockito.when(authentication.getName()).thenReturn(MAIL);
        userService.setAuthentication(authentication);
        //Ejercitar
        try {
            userService.unbanUser(USER_ID + 1);
        } catch (NotFoundException e) {
            return;
        } catch (UnauthorizedException e) {
            Assert.fail();
        }
        //Si no lanza excepcion, falla el test.
        Assert.fail();
    }
    @Test
    public void getUserAvatarByWrongIdTest() {
        //Setup
        Mockito.doReturn(Optional.empty()).when(mockDao).getUserAvatar(AdditionalMatchers.not(Mockito.eq(USER_ID)));
        //Ejercitar
        Optional<byte[]> avatar = userService.getUserAvatar(USER_ID + 1);
        //Asserts
        Assert.assertFalse(avatar.isPresent());
    }
}
