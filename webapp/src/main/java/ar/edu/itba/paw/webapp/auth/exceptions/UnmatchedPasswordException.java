package ar.edu.itba.paw.webapp.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UnmatchedPasswordException extends AuthenticationException {

    public UnmatchedPasswordException(String msg) {
        super(msg);
    }
}
