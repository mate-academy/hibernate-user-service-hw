package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String mail, String password) throws AuthenticationException;

    User register(String mail, String password) throws RegistrationException;
}
