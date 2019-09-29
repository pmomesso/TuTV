package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.MailService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
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

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User findByMail(String mail) {
        return userDao.getUserByMail(mail);
    }

    @Override
    public User createUser(String userName, String password, String mail) {
        //String hashedPassword = passwordEncoder.encode(password);
        User u = userDao.createUser(userName, password, mail);
        //TODO CHEQUEAR QUE NO CONCIDAN MAILS O USERNAMES CON OTROS USUARIOS EXISTENTES
        String token = null;
        boolean flag = false;
        while(!flag) {
            token = UUID.randomUUID().toString(); //TODO ESTO ESTA BIEN? NO PUEDO ENTRAR EN UN LOOP SI NO CAMBIA LA SEMILLA?
            flag = !userDao.checkIfValidationKeyExists(token);
        }

        userDao.setValidationKey(u, token);

        mailService.sendConfirmationMail(u, token);

        return u;
    }

    @Override
    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserMail = authentication.getName();

            User u = findByMail(currentUserMail);

            return u;
        }

        return null;
    }
}
