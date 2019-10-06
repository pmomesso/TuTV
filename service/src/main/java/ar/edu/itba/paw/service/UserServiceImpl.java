package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.MailService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Hello world!
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Authentication authentication = null;

    @Autowired
    public UserServiceImpl(UserDao userDao,MailService mailService,PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(long id) throws NotFoundException {
        return userDao.getUserById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Optional<User> findByMail(String mail) {
        return userDao.getUserByMail(mail);
    }

    @Override
    public User createUser(String userName, String password, String mail, boolean isAdmin, String baseUrl) {
        String hashedPassword = passwordEncoder.encode(password);
        return userDao.createUser(userName, hashedPassword, mail, isAdmin).map(user -> {
            String token = UUID.randomUUID().toString();
            userDao.setValidationKey(user.getId(), token);
            mailService.sendConfirmationMail(user, token, baseUrl);
            return user;
        }).get();
        //TODO CHEQUEAR QUE NO CONCIDAN MAILS O USERNAMES CON OTROS USUARIOS EXISTENTES
        //TODO ESTO ESTA BIEN? NO PUEDO ENTRAR EN UN LOOP SI NO CAMBIA LA SEMILLA?
    }

    @Override
    public Optional<User> getLoggedUser() {
        Authentication authentication = this.authentication;
        if(authentication == null)
            authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserMail = authentication.getName();
            return findByMail(currentUserMail);
        }
        return Optional.empty();
    }

    @Override
    public void banUser(long userId) throws UnauthorizedException, NotFoundException {
        User user = getLoggedUser().orElseThrow(NotFoundException::new);
        if(!user.getIsAdmin()) throw new UnauthorizedException();
        int result = userDao.banUser(userId);
        if(result == -1) {
            throw new NotFoundException();
        }
    }

    @Override
    public void unbanUser(long userId) throws UnauthorizedException, NotFoundException {
        User user = getLoggedUser().orElseThrow(NotFoundException::new);
        if(!user.getIsAdmin()) throw new UnauthorizedException();
        int result = userDao.unbanUser(userId);
        if(result == -1) {
            throw new NotFoundException();
        }
    }

    void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public boolean setUserAvatar(long userId, byte[] byteArray) {
        //TODO validar byteArray
        userDao.setUserAvatar(userId, byteArray);
        return true;
    }

    @Override
    public byte[] getUserAvatar(long userId) {
        return userDao.getUserAvatar(userId);
    }

    @Override
    public boolean activateUser(String token) {
        Optional<User> u = userDao.getUserByValidationKey(token);

        if(!u.isPresent()) return false;

        userDao.setValidationKey(u.get().getId(), null);
        return true;
    }
}
