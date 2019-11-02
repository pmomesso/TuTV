package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.MailService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    public Optional<User> findById(long id){
        return userDao.getUserById(id);
    }

    @Override
    public Optional<User> findByMail(String mail) {
        return userDao.getUserByMail(mail);
    }

    @Override
    @Transactional
    public Either<User, Collection<Errors>> createUser(String userName, String password, String mail, boolean isAdmin, String baseUrl) {
        boolean usernameExists = userDao.userNameExists(userName);
        boolean mailIsTaken = userDao.mailIsTaken(mail);
        if(usernameExists || mailIsTaken){
            List<Errors> errors = new ArrayList<>();
            if(usernameExists)
                errors.add(Errors.USERNAME_ALREADY_IN_USE);
            if(mailIsTaken)
                errors.add(Errors.MAIL_ALREADY_IN_USE);
            return Either.alternative(errors);
        }

        String hashedPassword = passwordEncoder.encode(password);
        User u = userDao.createUser(userName, hashedPassword, mail, isAdmin).get();
        String token = UUID.randomUUID().toString();
        userDao.setValidationKey(u.getId(), token);
        u.setConfirmationKey(token);
        mailService.sendConfirmationMail(u, baseUrl);
        //if(u == null) {
        //    throw new UnauthorizedException();
        //}
        return Either.value(u);
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
    @Transactional
    public List<User> getAllUsersExceptLoggedOne() {
        Optional<User> loggedUser = getLoggedUser();
        List<User> usersList = userDao.getAllUsers();
        return usersList.stream().filter(user -> loggedUser.isPresent() ? (loggedUser.get().getId() != user.getId()) : true).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void banUser(long userId) throws UnauthorizedException, NotFoundException {
        User user = getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(!user.getIsAdmin()) throw new UnauthorizedException();
        int result = userDao.banUser(userId);
        if(result == -1) {
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional
    public boolean updateLoggedUserName(String newUsername) throws NotFoundException {
        User user = getLoggedUser().orElseThrow(NotFoundException::new);
        int result = userDao.updateUserName(user.getId(),newUsername);
        return result == 1;
    }

    @Override
    @Transactional
    public void unbanUser(long userId) throws UnauthorizedException, NotFoundException {
        User user = getLoggedUser().orElseThrow(UnauthorizedException::new);
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
    @Transactional
    public void setUserAvatar(long userId, byte[] byteArray) {
        userDao.setUserAvatar(userId, byteArray);
    }

    @Override
    @Transactional
    public Optional<byte[]> getUserAvatar(long userId) {
        return userDao.getUserAvatar(userId);
    }

    @Override
    @Transactional
    public boolean activateUser(String token) {
        Optional<User> u = userDao.getUserByValidationKey(token);
        u.ifPresent(user -> userDao.setValidationKey(user.getId(), null));
        return u.isPresent();
    }
}
