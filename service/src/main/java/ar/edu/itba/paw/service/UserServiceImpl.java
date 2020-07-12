package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.AuthenticationService;
import ar.edu.itba.paw.interfaces.MailService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.UsersList;
import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final List<String> supportedExtensions = Arrays.asList("png","jpg","jpeg");

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao,MailService mailService,PasswordEncoder passwordEncoder,AuthenticationService authenticationService) {
        this.userDao = userDao;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
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
    public Either<User, Collection<Errors>> createUser(String userName, String password, String mail, boolean isAdmin, String baseUrl) throws UnauthorizedException {
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
        User u = userDao.createUser(userName, hashedPassword, mail, isAdmin).orElseThrow(UnauthorizedException::new);
        String token = UUID.randomUUID().toString();
        userDao.setValidationKey(u.getId(), token);
        u.setConfirmationKey(token);
        mailService.sendConfirmationMail(u, baseUrl);
        return Either.value(u);
    }

    @Override
    public Optional<User> getLoggedUser() {
        Optional<String> loggedUserMail;
        try{
            loggedUserMail = authenticationService.getLoggedUserMail();
        }catch(Exception ex){
            return Optional.empty();
        }

        if(!loggedUserMail.isPresent())
            return Optional.empty();

        Optional<User> ret = findByMail(loggedUserMail.get());
        ret.ifPresent(user -> user.setNotificationsToView(user.getNotifications().stream().filter(n -> !n.getViewed()).count()));

        return ret;
    }

    @Override
    @Transactional
    public UsersList getAllUsersExceptLoggedOne(int page, int pageSize) throws UnauthorizedException {
        User loggedUser = getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(loggedUser.getIsBanned()) throw new UnauthorizedException();
        return userDao.getAllUsers(page, pageSize, loggedUser.getId());
    }

    @Override
    @Transactional
    public Map<Genre, Long> getGenresStats() throws UnauthorizedException {
        Optional<User> loggedUser = getLoggedUser();
        return userDao.getGenreStats(loggedUser.orElseThrow(UnauthorizedException::new).getId());
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
    public boolean updateLoggedUserName(String newUsername) throws UnauthorizedException {
        User user = getLoggedUser().orElseThrow(UnauthorizedException::new);
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

    //Todo no depender más de este método
    void setAuthentication(Authentication authentication) {
        //this.authentication = authentication;
    }

    @Override
    @Transactional
    public void setUserAvatar(long userId, String base64Image) throws BadRequestException{
        //String extension = FilenameUtils.getExtension(fileName);
        //if(!supportedExtensions.contains(extension)){
        //    throw new BadRequestException();
        //}
        byte[] image = Base64.getDecoder().decode(base64Image);
        userDao.setUserAvatar(userId, image);
    }

    @Override
    @Transactional
    public Optional<byte[]> getUserAvatar(long userId) {
        return userDao.getUserAvatar(userId);
    }

    @Override
    @Transactional
    public Optional<User> activateUser(String token) {
        Optional<User> u = userDao.getUserByValidationKey(token);
        if(u.isPresent()) {
            userDao.setValidationKey(u.get().getId(), null);
        }

        return u;
    }

    @Override
    @Transactional
    public void setNotificationViewed(long notificationId, boolean viewed) throws NotFoundException, UnauthorizedException {
        User user = getLoggedUser().get();
        if(!user.getNotifications().stream().filter(notification -> notification.getId() == notificationId).findAny().isPresent())
            throw new UnauthorizedException();
        if(!userDao.setNotificationViewed(notificationId,viewed)) throw new NotFoundException();
    }

    @Override
    @Transactional
    public void deleteNotification(long userId, long notificationId) throws NotFoundException {
        if(!userDao.deleteNotification(userId,notificationId)){
            throw new NotFoundException();
        }
    }
}
