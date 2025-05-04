package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String email, String password) throws ArithmeticException, AuthenticationException;

    User register(String email, String password) throws RegistrationException;
}

