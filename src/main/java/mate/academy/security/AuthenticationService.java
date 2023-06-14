package mate.academy.security;

import javax.naming.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;

    User register(String email, String password) throws RegistrationException;
}
