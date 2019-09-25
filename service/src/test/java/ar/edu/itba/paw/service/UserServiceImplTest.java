package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MAIL = "mail";
    private static final long USER_ID = 1;
    @Mock
    private UserDao mockDao;
    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl(mockDao);

    @Test
    public void testCreateUser(){
        // 1. Setup!
        Mockito.when(mockDao.createUser(Mockito.eq(USERNAME), Mockito.eq(PASSWORD), Mockito.eq(MAIL)))
                .thenReturn(USER_ID);
        // 2. "ejercito" la class under test
        long id  = userService.createUser(USERNAME, PASSWORD, MAIL);
        // 3. Asserts!
        Assert.assertEquals(id, USER_ID);
    }
    @Test
    public void testFindById(){
        // 1. Setup!
        User mockUser = new User();
        mockUser.setUserName(USERNAME);
        mockUser.setId(USER_ID);
        mockUser.setPassword(PASSWORD);
        mockUser.setMailAddress(MAIL);
        Mockito.when(mockDao.getUser(USER_ID))
                .thenReturn(mockUser);
        // 2. "ejercito" la class under test
        User user = userService.findById(USER_ID);
        // 3. Asserts!
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getUserName(),USERNAME);
    }

}
