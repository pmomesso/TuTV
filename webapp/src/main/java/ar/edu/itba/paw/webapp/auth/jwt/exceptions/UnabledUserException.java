package ar.edu.itba.paw.webapp.auth.jwt.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UnabledUserException extends AuthenticationException {
    public UnabledUserException(String msg) {
        super(msg);
    }
}
