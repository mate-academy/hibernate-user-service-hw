package mate.academy.security;

import mate.academy.model.User;
import mate.academy.model.exception.AuthenticationException;
import mate.academy.model.exception.RegistrationException;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;

    void register(String email, String password) throws RegistrationException;
}
