package mate.academy.security;

import mate.academy.exceptions.AuthenticationException;
import mate.academy.exceptions.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    User register(String email, String password) throws RegistrationException;
}
