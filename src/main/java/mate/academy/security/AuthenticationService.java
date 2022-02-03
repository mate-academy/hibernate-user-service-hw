package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User registerUser(String email, String password) throws RegistrationException;

    User login(String login, String password) throws AuthenticationException;
}
