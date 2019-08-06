package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Service;

/**
 * Hello world!
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findById(long id) {
        return null;
    }
}
