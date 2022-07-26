package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.exception.RegistrationValidationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String email, String password)
            throws AuthenticationException, RegistrationValidationException;

    User register(String email, String password)
            throws RegistrationException, RegistrationValidationException;
}
