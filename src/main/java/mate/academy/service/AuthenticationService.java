package mate.academy.service;

import mate.academy.exception.UserAuthenticationException;
import mate.academy.exception.UserRegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String email, String password) throws UserAuthenticationException;

    User register(String email, String password) throws UserRegistrationException;
}
