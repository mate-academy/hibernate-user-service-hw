package mate.academy.security;

import mate.academy.exception.AuthenticationFailureException;
import mate.academy.exception.RegistrationFailureException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationFailureException;

    User register(String email, String password) throws RegistrationFailureException;
}
