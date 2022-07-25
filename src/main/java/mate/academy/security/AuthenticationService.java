package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataValidationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User register(String email, String password)
            throws RegistrationException, DataValidationException;

    User login(String email, String password) throws AuthenticationException;
}
